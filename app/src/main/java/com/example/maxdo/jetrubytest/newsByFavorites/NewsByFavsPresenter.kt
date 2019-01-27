package com.example.maxdo.jetrubytest.newsByFavorites

import com.example.maxdo.jetrubytest.core.entities.Article
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable

class NewsByFavsPresenter : MviBasePresenter<NewsByFavsView, NewsByFavsViewState>() {

    override fun bindIntents() {

        subscribeViewState(Observable.empty(), NewsByFavsView::render)
    }

    private fun stateReducer(initialState: NewsByFavsViewState, partialState: PartialVS): NewsByFavsViewState {

        if (partialState is PartialVS.Loading) {
            return initialState.builder().withArticles(listOf()).withError(null).withProgress(true)
                .build()
        }

        if (partialState is PartialVS.FirstShow) {
            return initialState.builder().withArticles(partialState.articles).withError(null).withProgress(false)
                .build()
        }

        if (partialState is PartialVS.EmptySourcesList) {
            return NewsByFavsViewState(listOf(), false, null)
        }


        throw IllegalStateException("Unknown Partial!")
    }


    sealed class PartialVS {
        class Loading(val progress: Boolean) : PartialVS()
        class FirstShow(val articles: List<Article>, val progress: Boolean, val error: String?) : PartialVS()
        class EmptySourcesList() : PartialVS()
    }

}
