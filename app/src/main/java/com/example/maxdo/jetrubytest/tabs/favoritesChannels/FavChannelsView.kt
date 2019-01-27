package com.example.maxdo.jetrubytest.tabs.favoritesChannels

import com.example.maxdo.jetrubytest.core.entities.Source
import com.example.maxdo.jetrubytest.core.mvi.BaseView
import io.reactivex.Observable

interface FavChannelsView : BaseView<FavChannelsViewState> {
    fun getClickOnFavChannelIntent(): Observable<Source>
    fun getDismissFavChannelRemovingDialogIntent(): Observable<Boolean>
    fun getSubmitRemovingFromFavChannelsDialogIntent(): Observable<Source>
    fun getNotifyDoneMessageShowedOnceIntent(): Observable<Boolean>
    fun pullToRefreshFavChannelsIntent(): Observable<Boolean>
    fun refreshFromOutsideIntent(): Observable<Boolean>
}
