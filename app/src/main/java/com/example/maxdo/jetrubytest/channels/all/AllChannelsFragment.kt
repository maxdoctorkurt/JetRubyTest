package com.example.maxdo.jetrubytest.channels.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.maxdo.jetrubytest.R
import com.hannesdorfmann.mosby3.mvi.MviFragment

class AllChannelsFragment: MviFragment<AllChannelsView, AllChannelsPresenter>(), AllChannelsView {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.all_channels, container, false)
    }

    override fun createPresenter(): AllChannelsPresenter {
        return AllChannelsPresenter()
    }

    override fun render(viewState: AllChannelsViewState) {

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true
    }
}
