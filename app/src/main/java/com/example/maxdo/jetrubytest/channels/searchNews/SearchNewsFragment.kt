package com.example.maxdo.jetrubytest.channels.searchNews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.maxdo.jetrubytest.R
import com.hannesdorfmann.mosby3.mvi.MviFragment

class SearchNewsFragment : MviFragment<SearchNewsView, SearchNewsPresenter>(), SearchNewsView {
    override fun createPresenter(): SearchNewsPresenter {
        return SearchNewsPresenter()
    }

    override fun render(viewState: SearchNewsViewState) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.search_news, container, false)
    }
}