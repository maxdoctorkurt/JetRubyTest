package com.example.maxdo.jetrubytest.channels.all

import com.example.maxdo.jetrubytest.core.Repository
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable

class AllChannelsPresenter : MviBasePresenter<AllChannelsView, AllChannelsViewState>() {



    override fun bindIntents() {


        Repository.everything()


        subscribeViewState(Observable.empty(), AllChannelsView::render)
    }

}
