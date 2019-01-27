package com.example.maxdo.jetrubytest.newsByFavorites

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.maxdo.jetrubytest.R
import com.example.maxdo.jetrubytest.tabs.NewsAdapter
import com.hannesdorfmann.mosby3.mvi.MviActivity
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.news_by_favorites_activity.*

class NewsByFavoritesChannelsActivity : MviActivity<NewsByFavsView, NewsByFavsPresenter>(), NewsByFavsView {

    private lateinit var adapter: NewsAdapter
    private val subject = PublishSubject.create<Boolean>()

    override fun pullToRefreshIntent(): Observable<Boolean> {

        swipeArticlesByFavChannels.setOnRefreshListener {
            subject.onNext(true)
        }

        return subject
    }

    override fun render(viewState: NewsByFavsViewState) {

        adapter.setData(viewState.articles)

        swipeArticlesByFavChannels.isRefreshing = viewState.progress

        if (viewState.error != null)
            Toast.makeText(this, viewState.error, Toast.LENGTH_LONG).show()

        noElementsWrapper.visibility = if (viewState.articles.isEmpty())
            View.VISIBLE
        else
            View.GONE

    }

    override fun createPresenter(): NewsByFavsPresenter {
        return NewsByFavsPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.news_by_favorites_activity)

        adapter = NewsAdapter(this)
        articlesByFavChannels.adapter = adapter
        articlesByFavChannels.layoutManager = LinearLayoutManager(this)

    }

}