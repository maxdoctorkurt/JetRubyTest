package com.example.maxdo.jetrubytest.core

import com.example.maxdo.jetrubytest.App
import com.example.maxdo.jetrubytest.core.entities.Source
import com.example.maxdo.jetrubytest.core.responses.EverythingResponse
import com.example.maxdo.jetrubytest.core.responses.SourcesResponse
import io.reactivex.Single

object Repository {

    private val api = App.instance?.apiCalls?.api
    private val db = App.instance?.db

    fun everything(query: String, pageSize: Int): Single<EverythingResponse> {
        return api?.everything(query, pageSize) ?: Single.just(EverythingResponse(null, null, null))
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
}