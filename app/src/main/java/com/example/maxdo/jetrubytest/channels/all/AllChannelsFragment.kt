package com.example.maxdo.jetrubytest.channels.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.maxdo.jetrubytest.R
import com.hannesdorfmann.mosby3.mvi.MviFragment
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.all_channels.*

class AllChannelsFragment : MviFragment<AllChannelsView, AllChannelsPresenter>(), AllChannelsView {

    lateinit var adapter: AllChannelsAdapter

    override fun getClickOnChannelIntent(): Observable<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getDissmissFavChannelDialog(): Observable<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPullToRefreshIntent(): Observable<Boolean> {

        val subject = PublishSubject.create<Boolean>()
        swipeRefresh.setOnRefreshListener {
            subject.onNext(true)

            println("*** swipe!!!")
        }
        return subject
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.all_channels, container, false)
    }

    override fun createPresenter(): AllChannelsPresenter {
        return AllChannelsPresenter()
    }

    override fun render(viewState: AllChannelsViewState) {

        if (viewState.channels.isNotEmpty()) {
            adapter.setData(viewState.channels)
        }

        swipeRefresh.isRefreshing = viewState.progress

        if (viewState.error != null)
            Toast.makeText(context, viewState.error, Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = AllChannelsAdapter(context!!)
        allChannels.adapter = adapter
        allChannels.layoutManager = LinearLayoutManager(context)
    }
}
