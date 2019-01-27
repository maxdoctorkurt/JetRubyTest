package com.example.maxdo.jetrubytest.tabs.searchNews

import com.example.maxdo.jetrubytest.core.Repository
import com.example.maxdo.jetrubytest.core.entities.Article
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
                return@flatMap Repository.topHeadlines(it, 30).toObservable().cache()
            }
            .map {
                if (it.articles != null) return@map PartialVS.Search(it.articles) as PartialVS
                return@map PartialVS.Search(listOf())
            }
            .onErrorResumeNext { throwable: Throwable ->
                onError(throwable)
            }
            .startWith(PartialVS.Loading())

        val observable: Observable<SearchNewsViewState> =
            searchPartial
                .scan(initialState, this::stateReducer)
                .startWith(
                    SearchNewsViewState(listOf(), true, null)
                ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(observable, SearchNewsView::render)
    }

    private fun onError(throwable: Throwable): Observable<PartialVS> {
        return Observable.just(
            PartialVS.Search(
                listOf(), throwable.message + throwable.localizedMessage

            )
        )
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
