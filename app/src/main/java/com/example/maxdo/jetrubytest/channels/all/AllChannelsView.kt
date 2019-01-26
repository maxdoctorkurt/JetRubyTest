package com.example.maxdo.jetrubytest.channels.all

import com.example.maxdo.jetrubytest.core.mvi.BaseView
import io.reactivex.Observable

interface AllChannelsView : BaseView<AllChannelsViewState>{

    fun getPullToRefreshIntent(): Observable<Boolean>
    fun getClickOnChannelIntent(): Observable<Boolean>
    fun getDissmissFavChannelDialog(): Observable<Boolean>
}
