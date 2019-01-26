package com.example.maxdo.jetrubytest.channels.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onDismiss
import com.example.maxdo.jetrubytest.R
import com.example.maxdo.jetrubytest.channels.ChannelsAdapter
import com.example.maxdo.jetrubytest.core.entities.Source
import com.hannesdorfmann.mosby3.mvi.MviFragment
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.all_channels.*

class AllChannelsFragment : MviFragment<AllChannelsView, AllChannelsPresenter>(), AllChannelsView {
    private lateinit var adapter: ChannelsAdapter

    private lateinit var addToFavDialog: MaterialDialog
    private val adapterItemClickSubject = PublishSubject.create<Source>()  // give to adapter

    private var addToFavDialogSubmit = PublishSubject.create<Source>()
    private var addToFavDialogDismiss = PublishSubject.create<Boolean>()
    private var notifyDoneMessageShowedOnce = PublishSubject.create<Boolean>()

    override fun getNotifyDoneMessageShowedOnceIntent(): Observable<Boolean> {
        return notifyDoneMessageShowedOnce
    }

    override fun getClickOnChannelIntent(): Observable<Source> {
        return adapterItemClickSubject
    }

    override fun getDismissFavChannelDialogIntent(): Observable<Boolean> {
        return addToFavDialogDismiss
    }

    override fun getSubmitAddingToFavChannelsDialogIntent(): Observable<Source> {
        return addToFavDialogSubmit
    }

    override fun getPullToRefreshIntent(): Observable<Boolean> {

        val subject = PublishSubject.create<Boolean>()
        allChannelsSwipeRefresh.setOnRefreshListener {
            subject.onNext(true)
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

        // TODO - check wifi connection
        noWifiPlaceholder.visibility = View.GONE

        if (viewState.channels.isNotEmpty()) {
            allChannels.visibility = View.VISIBLE
            noElemPlaceholder.visibility = View.GONE
            adapter.setData(viewState.channels)
        } else {
            allChannels.visibility = View.GONE
            noElemPlaceholder.visibility = View.VISIBLE
        }

        allChannelsSwipeRefresh.isRefreshing = viewState.progress

        if (viewState.error != null)
            Toast.makeText(context, viewState.error, Toast.LENGTH_LONG).show()
        val source = viewState.sourceRelatedToDialog

        if (source != null) {
            addToFavDialog.title(R.string.adding_to_fav_dialog_title)

            val msg = " ${getString(R.string.adding_to_fav_dialog_message)}\n\r[${source.name}]"
            addToFavDialog.message(null, msg)
                .positiveButton(R.string.ok, null) {
                    addToFavDialogSubmit.onNext(source)
                }
                .onDismiss {
                    addToFavDialogDismiss.onNext(true)
                }
                .show()
        } else {
            addToFavDialog.hide()
        }

        val addToFavsDoneMessage = viewState.successAddingToFavoritesMessage

        if (addToFavsDoneMessage != null) {
            Toast.makeText(context, addToFavsDoneMessage, Toast.LENGTH_LONG).show()
            notifyDoneMessageShowedOnce.onNext(true)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ChannelsAdapter(context!!, adapterItemClickSubject)
        allChannels.adapter = adapter
        allChannels.layoutManager = LinearLayoutManager(context)

        addToFavDialog = MaterialDialog(context!!)
        addToFavDialog
            .negativeButton(R.string.cancel, null) {
                it.dismiss()
            }
    }
}
