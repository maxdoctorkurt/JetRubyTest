package com.example.maxdo.jetrubytest.newsByFavorites

import com.hannesdorfmann.mosby3.mvi.MviActivity

class NewsByFavoritesChannelsActivity : MviActivity<NewsByFavsView, NewsByFavsPresenter>(), NewsByFavsView {

    override fun render(viewState: NewsByFavsViewState) {

    }

    override fun createPresenter(): NewsByFavsPresenter {
        return NewsByFavsPresenter()
    }
}