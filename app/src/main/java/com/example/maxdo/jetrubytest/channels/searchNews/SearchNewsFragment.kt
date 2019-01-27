package com.example.maxdo.jetrubytest.channels.searchNews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.maxdo.jetrubytest.R
import com.example.maxdo.jetrubytest.channels.ChannelsAdapter
import com.example.maxdo.jetrubytest.channels.NewsAdapter
import com.hannesdorfmann.mosby3.mvi.MviFragment
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.search_news.*

class SearchNewsFragment : MviFragment<SearchNewsView, SearchNewsPresenter>(), SearchNewsView {

    lateinit var adapter: NewsAdapter

    override fun getSearchOutsideIntent(): Observable<String> {
        return searchOutsidePublisher
    }

    companion object {
        val searchOutsidePublisher = PublishSubject.create<String>()
    }

    override fun createPresenter(): SearchNewsPresenter {
        return SearchNewsPresenter()
    }

    override fun render(viewState: SearchNewsViewState) {
        adapter.setData(viewState.articles)

        if(viewState.error != null)
            Toast.makeText(context, viewState.error, Toast.LENGTH_LONG).show()

        if(viewState.articles.isEmpty()) {
            noElemPlaceholder.visibility = View.VISIBLE
        }
        else {
            noElemPlaceholder.visibility = View.GONE

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.search_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = NewsAdapter(context!!)

        newsSearched.adapter = adapter
        newsSearched.layoutManager = LinearLayoutManager(context)
    }
}