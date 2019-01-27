package com.example.maxdo.jetrubytest.tabs.allChannels

import com.example.maxdo.jetrubytest.core.entities.Source
import com.example.maxdo.jetrubytest.core.mvi.BaseView
import io.reactivex.Observable

interface AllChannelsView : BaseView<AllChannelsViewState>{
    fun getPullToRefreshIntent(): Observable<Boolean>
    fun getClickOnChannelIntent(): Observable<Source>
    fun getDismissFavChannelDialogIntent(): Observable<Boolean>
    fun getSubmitAddingToFavChannelsDialogIntent(): Observable<Source>
    fun getNotifyDoneMessageShowedOnceIntent(): Observable<Boolean>
}
