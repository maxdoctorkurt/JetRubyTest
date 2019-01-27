package com.example.maxdo.jetrubytest.newsByFavorites

import com.example.maxdo.jetrubytest.core.mvi.BaseView
import io.reactivex.Observable

interface NewsByFavsView: BaseView<NewsByFavsViewState> {

    fun pullToRefreshIntent(): Observable<Boolean>
}
