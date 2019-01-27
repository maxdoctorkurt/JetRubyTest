package com.example.maxdo.jetrubytest.core

import com.example.maxdo.jetrubytest.App
import com.example.maxdo.jetrubytest.core.entities.Article
import com.example.maxdo.jetrubytest.core.entities.Source
import com.example.maxdo.jetrubytest.core.responses.EverythingResponse
import com.example.maxdo.jetrubytest.core.responses.SourcesResponse
import io.reactivex.Observable
import io.reactivex.Single

object Repository {

    private val api = App.instance?.apiCalls?.api
    private val db = App.instance?.db

    fun everythingBySources(sources: String): Single<EverythingResponse> {
        return api?.everythingBySources(sources) ?: Single.just(EverythingResponse(null, null, null))
    }

    fun topHeadlines(query: String, pageSize: Int): Single<EverythingResponse> {
        return api?.topHeadlines(query, pageSize) ?: Single.just(EverythingResponse(null, null, null))
    }

    fun sources(): Single<SourcesResponse> {
        return api?.sources() ?: Single.just(SourcesResponse(null, null))
    }

    fun getFavSources(): Single<List<Source>> {
        return Single.defer { Single.just(db?.sourceDao?.getAllSources() ?: listOf()) }
    }

    fun addSourceToFav(toAdd: Source): Single<Boolean> {
        return Single.defer { Single.just(db?.sourceDao?.add(toAdd)) }.map {
            true
        }
    }

    fun removeSourceFromFav(toRemove: Source): Single<Boolean> {
        return Single.defer { Single.just(db?.sourceDao?.delete(toRemove)) }.map { true }
    }

    fun getArticlesByFavoriteSources(): Single<List<Article>> {
        
        val articlesFromInternet: Single<List<Article>> =
            getFavSources()
                .toObservable()
                .flatMap {
                    Observable.fromIterable(it)
                }
                .filter {
                    it.name != null
                }
                .flatMap {
                    everythingBySources(it.name!!).toObservable()
                }
                .filter {
                    it.articles != null
                }
                .toList()
                .toObservable()
                .doOnNext {
                    // clean old cache only when request is success
                    db?.articleDao?.removeAllArticles()
                }
                .flatMap {
                    Observable.fromIterable(it)
                }
                .flatMap { everythingResponse ->
                    Observable.fromIterable(everythingResponse.articles!!).map {
                        // caching new articles
                        println("*** add to cache!!!")
                        db?.articleDao?.add(it)
                        it
                    }
                }
                .toList().onErrorResumeNext(Single.just(listOf<Article>()))

        val articlesFromDB: Single<List<Article>> =
            Single.defer { Single.just(db?.articleDao?.getAllArticles() ?: listOf()) }

        return articlesFromInternet.concatWith(articlesFromDB).filter {
            it.isNotEmpty()
        }.first(listOf())

    }

}