package com.example.maxdo.jetrubytest.newsByFavorites

import com.example.maxdo.jetrubytest.core.Repository
import com.example.maxdo.jetrubytest.core.entities.Article
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class NewsByFavsPresenter : MviBasePresenter<NewsByFavsView, NewsByFavsViewState>() {

    override fun bindIntents() {

        val initialState = NewsByFavsViewState()
        val partialListLoading: Observable<PartialVS> = intent(NewsByFavsView::pullToRefreshIntent)
            .mergeWith(Observable.just(true)) // load automatically on first time
            .observeOn(Schedulers.io())
            .debounce(300, TimeUnit.MILLISECONDS)
            .flatMap {
                getArticlesAccordingToFavoriteChannels(initialState)
            }

        val observable: Observable<NewsByFavsViewState> =
            partialListLoading

                .scan(initialState, this::stateReducer)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(observable, NewsByFavsView::render)
    }

    private fun getArticlesAccordingToFavoriteChannels(initialState: NewsByFavsViewState): Observable<PartialVS> {
        return Repository.getArticlesByFavoriteSources().toObservable().cache()
            .flatMap {

                if (initialState.articles.isEmpty())
                    Observable.just(PartialVS.ShowList(it, false, null) as PartialVS)

                val o1: Observable<PartialVS> = Observable.just(PartialVS.Loading(true))
                val o2: Observable<PartialVS> = Observable.just(PartialVS.ShowList(it, false, null))

                o1.concatWith(o2)
            }
            .onErrorResumeNext { t: Throwable -> Observable.just(PartialVS.ShowList(listOf(), false, t.message)) }
    }

    private fun stateReducer(initialState: NewsByFavsViewState, partialState: PartialVS): NewsByFavsViewState {

        if (partialState is PartialVS.Loading) {
            return initialState.builder().withArticles(listOf()).withError(null).withProgress(true)
                .build()
        }

        if (partialState is PartialVS.ShowList) {
            return initialState.builder().withArticles(partialState.articles).withError(partialState.error)
                .withProgress(false)
                .build()
        }

        throw IllegalStateException("Unknown Partial!")
    }


    sealed class PartialVS {
        class Loading(val progress: Boolean) : PartialVS()
        class ShowList(val articles: List<Article>, val progress: Boolean, val error: String?) : PartialVS()
    }

}
