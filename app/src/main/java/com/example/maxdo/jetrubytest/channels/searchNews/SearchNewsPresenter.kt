package com.example.maxdo.jetrubytest.channels.searchNews

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable

class SearchNewsPresenter : MviBasePresenter<SearchNewsView, SearchNewsViewState>() {
    override fun bindIntents() {
        subscribeViewState(Observable.empty(), SearchNewsView::render)
    }

}
