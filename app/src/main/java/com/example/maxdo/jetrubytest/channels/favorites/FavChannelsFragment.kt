package com.example.maxdo.jetrubytest.channels.favorites

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
import kotlinx.android.synthetic.main.fav_channels.*

class FavChannelsFragment : MviFragment<FavChannelsView, FavChannelsPresenter>(), FavChannelsView {
    lateinit var adapter: ChannelsAdapter

    private lateinit var removeFromFavDialog: MaterialDialog

    // give to adapter
    private val favItemClickSubject = PublishSubject.create<Source>()
    private var removeFromFavDialogSubmit = PublishSubject.create<Source>()
    private var removeFromFavDialogDismiss = PublishSubject.create<Boolean>()
    private var notifyDoneMessageShowedOnce = PublishSubject.create<Boolean>()

    override fun getSubmitRemovingFromFavChannelsDialogIntent(): Observable<Source> {
        return removeFromFavDialogSubmit
    }

    override fun getNotifyDoneMessageShowedOnceIntent(): Observable<Boolean> {
        return notifyDoneMessageShowedOnce
    }

    override fun getClickOnFavChannelIntent(): Observable<Source> {
        return favItemClickSubject
    }

    override fun getDismissFavChannelRemovingDialogIntent(): Observable<Boolean> {
        return removeFromFavDialogDismiss
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

        val source = viewState.sourceRelatedToDialog

        if (source != null) {
            removeFromFavDialog.title(R.string.removing_from_fav_dialog_title)

            val msg = " ${getString(R.string.removing_from_fav_dialog_message)}\n\r[${source.name}]"
            removeFromFavDialog.message(null, msg)
                .positiveButton(R.string.ok, null) {
                    removeFromFavDialogSubmit.onNext(source)
                }
                .onDismiss {
                    removeFromFavDialogDismiss.onNext(true)
                }
                .show()
        } else {
            removeFromFavDialog.hide()
        }

        val removeFromFavsDoneMessage = viewState.successRemovingFromFavoritesMessage

        if (removeFromFavsDoneMessage != null) {
            Toast.makeText(context, removeFromFavsDoneMessage, Toast.LENGTH_LONG).show()
            notifyDoneMessageShowedOnce.onNext(true)
        }

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

        removeFromFavDialog = MaterialDialog(context!!)
        removeFromFavDialog
            .negativeButton(R.string.cancel, null) {
                it.dismiss()
            }
    }

}
