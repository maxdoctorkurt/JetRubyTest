package com.example.maxdo.jetrubytest.newsByFavorites

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable

class NewsByFavsPresenter : MviBasePresenter<NewsByFavsView, NewsByFavsViewState>() {

    override fun bindIntents() {




        subscribeViewState(Observable.empty(), NewsByFavsView::render)
    }

}
