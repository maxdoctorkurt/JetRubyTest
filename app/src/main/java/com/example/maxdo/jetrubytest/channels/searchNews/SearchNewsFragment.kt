package com.example.maxdo.jetrubytest.channels.searchNews

import com.hannesdorfmann.mosby3.mvi.MviFragment

class SearchNewsFragment : MviFragment<SearchNewsView, SearchNewsPresenter>(), SearchNewsView {
    override fun createPresenter(): SearchNewsPresenter {
        return SearchNewsPresenter()
    }

    override fun render(viewState: SearchNewsViewState) {
    }
}