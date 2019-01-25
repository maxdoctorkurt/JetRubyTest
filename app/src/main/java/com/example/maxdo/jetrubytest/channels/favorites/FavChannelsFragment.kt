package com.example.maxdo.jetrubytest.channels.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.maxdo.jetrubytest.R
import com.hannesdorfmann.mosby3.mvi.MviFragment

class FavChannelsFragment : MviFragment<FavChannelsView, FavChannelsPresenter>(), FavChannelsView {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fav_channels, container, false)
    }

    override fun render(viewState: FavChannelsViewState) {
    }

    override fun createPresenter(): FavChannelsPresenter {
        return FavChannelsPresenter()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true
    }

}
