package com.example.maxdo.jetrubytest.tabs.searchNews

import com.example.maxdo.jetrubytest.core.mvi.BaseView
import io.reactivex.Observable

interface SearchNewsView: BaseView<SearchNewsViewState> {
    fun getSearchOutsideIntent(): Observable<String>
}
