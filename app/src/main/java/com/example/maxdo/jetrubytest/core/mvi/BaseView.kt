package com.example.maxdo.jetrubytest.core.mvi

import com.hannesdorfmann.mosby3.mvp.MvpView

interface BaseView<ViewStateClass>: MvpView {

    fun render(viewState: ViewStateClass)
}