package com.example.maxdo.jetrubytest.newsByFavorites

import android.os.Bundle
import android.os.PersistableBundle
import com.example.maxdo.jetrubytest.R
import com.hannesdorfmann.mosby3.mvi.MviActivity
import io.reactivex.Observable

class NewsByFavoritesChannelsActivity : MviActivity<NewsByFavsView, NewsByFavsPresenter>(), NewsByFavsView {


    override fun pullToRefreshIntent(): Observable<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun render(viewState: NewsByFavsViewState) {

    }

    override fun createPresenter(): NewsByFavsPresenter {
        return NewsByFavsPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        setContentView(R.layout.news_by_favorites_activity)
    }
}