package com.example.maxdo.jetrubytest.channels.favorites

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable

class FavChannelsPresenter : MviBasePresenter<FavChannelsView, FavChannelsViewState>() {
    override fun bindIntents() {
        subscribeViewState(Observable.empty(), FavChannelsView::render)
    }

}
