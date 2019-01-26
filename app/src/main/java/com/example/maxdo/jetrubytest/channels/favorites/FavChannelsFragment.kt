package com.example.maxdo.jetrubytest.channels.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.maxdo.jetrubytest.R
import com.example.maxdo.jetrubytest.channels.ChannelsAdapter
import com.example.maxdo.jetrubytest.core.entities.Source
import com.hannesdorfmann.mosby3.mvi.MviFragment
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fav_channels.*

class FavChannelsFragment : MviFragment<FavChannelsView, FavChannelsPresenter>(), FavChannelsView {
    lateinit var adapter: ChannelsAdapter

    // give to adapter
    private val favItemClickSubject = PublishSubject.create<Source>()

    override fun getClickOnFavChannelIntent(): Observable<Source> {
        return favItemClickSubject
    }

    override fun getDismissFavChannelRemovingDialogIntent(): Observable<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun pullToRefreshFavChannelsIntent(): Observable<Boolean> {
        val subject = PublishSubject.create<Boolean>()
        favChannelsSwipe.setOnRefreshListener {
            subject.onNext(true)
            println("*** swipe!!!")
        }
        return subject
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fav_channels, container, false)
    }

    override fun render(viewState: FavChannelsViewState) {

        favNoWifiPlaceholder.visibility = View.GONE

        if (viewState.channels.isNotEmpty()) {
            favChannels.visibility = View.VISIBLE
            favNoElemPlaceholder.visibility = View.GONE
            adapter.setData(viewState.channels)
        } else {
            favChannels.visibility = View.GONE
            favNoElemPlaceholder.visibility = View.VISIBLE
        }

        favChannelsSwipe.isRefreshing = viewState.progress

        if (viewState.error != null)
            Toast.makeText(context, viewState.error, Toast.LENGTH_LONG).show()
    }

    override fun createPresenter(): FavChannelsPresenter {
        return FavChannelsPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ChannelsAdapter(context!!, favItemClickSubject)
        favChannels.adapter = adapter
        favChannels.layoutManager = LinearLayoutManager(context)
    }

}
