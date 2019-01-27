package com.example.maxdo.jetrubytest.tabs.searchNews

import com.example.maxdo.jetrubytest.core.Repository
import com.example.maxdo.jetrubytest.core.entities.Article
import com.example.maxdo.jetrubytest.core.responses.EverythingResponse
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SearchNewsPresenter : MviBasePresenter<SearchNewsView, SearchNewsViewState>() {
    override fun bindIntents() {

        val initialState = SearchNewsViewState()

        val searchPartial: Observable<PartialVS> = intent(SearchNewsView::getSearchOutsideIntent)
            .observeOn(Schedulers.io())
            .debounce(1000, TimeUnit.MILLISECONDS)
            .flatMap {
                topHeadlines(it)
            }

        val observable: Observable<SearchNewsViewState> =
            searchPartial
                .scan(initialState, this::stateReducer)
                .startWith(
                    SearchNewsViewState(listOf(), true, null)
                ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(observable, SearchNewsView::render)
    }

    private fun topHeadlines(searchString: String): Observable<PartialVS> {

        return Repository.topHeadlines(searchString, 30).toObservable().cache()

            .flatMap {
                val o1: Observable<PartialVS> = Observable.just(PartialVS.Loading())
                val o2: Observable<PartialVS> = Observable.just(PartialVS.Search(it.articles!!, null))
                o1.concatWith(o2)
            }
            .onErrorResumeNext { t: Throwable -> Observable.just(PartialVS.Search(listOf(), t.message)) }
    }

    private fun stateReducer(initialState: SearchNewsViewState, partialState: PartialVS): SearchNewsViewState {

        if (partialState is PartialVS.Loading) {
            return initialState.builder().withArticles(listOf()).withError(null).withProgress(true)
                .build()
        }

        if (partialState is PartialVS.Search) {
            return initialState.builder().withArticles(partialState.articles).withError(partialState.error)
                .withProgress(true)
                .build()
        }

        throw IllegalStateException("Unknown Partial!")
    }

    sealed class PartialVS {
        class Search(val articles: List<Article>, val error: String? = null) : PartialVS()
        class Loading() : PartialVS()
    }

}
