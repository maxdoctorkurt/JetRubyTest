package com.example.maxdo.jetrubytest.tabs.allChannels

import com.example.maxdo.jetrubytest.tabs.favoritesChannels.FavChannelsFragment
import com.example.maxdo.jetrubytest.core.Repository
import com.example.maxdo.jetrubytest.core.entities.Source
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class AllChannelsPresenter : MviBasePresenter<AllChannelsView, AllChannelsViewState>() {

    override fun bindIntents() {

        val initialState = AllChannelsViewState()

        val partialPull: Observable<PartialVS> = intent(AllChannelsView::getPullToRefreshIntent)
            .mergeWith(Observable.just(true))
            .observeOn(Schedulers.io())
            .debounce(300, TimeUnit.MILLISECONDS)
            .flatMap {
                getSources()
            }

        val partialItemClick: Observable<PartialVS> = intent(AllChannelsView::getClickOnChannelIntent)
            .observeOn(Schedulers.io())
            .debounce(300, TimeUnit.MILLISECONDS)
            .map { PartialVS.ChannelSelection(it) }

        val partialDialogDismiss: Observable<PartialVS> = intent(AllChannelsView::getDismissFavChannelDialogIntent)
            .observeOn(Schedulers.io())
            .debounce(300, TimeUnit.MILLISECONDS)
            .map { PartialVS.FavoriteDialogDismiss() }

        val partialDialogSubmit: Observable<PartialVS> =
            intent(AllChannelsView::getSubmitAddingToFavChannelsDialogIntent)
                .observeOn(Schedulers.io())
                .debounce(300, TimeUnit.MILLISECONDS)
                .flatMap {
                    Repository.addSourceToFav(it).toObservable()
                }
                .map {
                    // tell a fragment that something has changed
                    FavChannelsFragment.refreshFromOutsideItemsSubject.onNext(true)
                    PartialVS.FavoriteDialogSubmit()
                }

        val partialDoneShowedOnce: Observable<PartialVS> = intent(AllChannelsView::getNotifyDoneMessageShowedOnceIntent)
            .observeOn(Schedulers.io())
            .debounce(300, TimeUnit.MILLISECONDS)
            .map {
                PartialVS.DoneMessageShowedOnce()
            }


        val observable: Observable<AllChannelsViewState> =
            partialPull
                .mergeWith(partialItemClick)
                .mergeWith(partialDialogDismiss)
                .mergeWith(partialDialogSubmit)
                .mergeWith(partialDoneShowedOnce)
                .scan(initialState, this::stateReducer)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(observable, AllChannelsView::render)

    }


    private fun getSources(): Observable<PartialVS> {
        return Repository.sources().toObservable().cache()
            .flatMap {
                val o1: Observable<PartialVS> = Observable.just(PartialVS.Loading(true))
                val o2: Observable<PartialVS> = Observable.just(PartialVS.ListShow(it.sources!!, false, null))
                o1.concatWith(o2)
            }
            .onErrorResumeNext { t: Throwable -> Observable.just(PartialVS.ListShow(listOf(), false, t.message)) }
    }

    private fun stateReducer(initialState: AllChannelsViewState, partialState: PartialVS): AllChannelsViewState {

        if (partialState is PartialVS.Loading) {
            return initialState.builder().withChannels(listOf()).withError(null).withProgress(true)
                .build()
        }

        if (partialState is PartialVS.ListShow) {
            return initialState.builder().withChannels(partialState.channels).withError(partialState.error).withProgress(false)
                .build()
        }

        if (partialState is PartialVS.PullToRefresh) {
            return initialState.builder().withChannels(partialState.channels).withError(null).withProgress(false)
                .build()
        }

        if (partialState is PartialVS.EmptySourcesList) {
            return AllChannelsViewState(listOf(), false, null, null)
        }

        if (partialState is PartialVS.ChannelSelection) {
            return initialState.builder().withAddFavouriteDialog(partialState.channel).build()
        }

        if (partialState is PartialVS.FavoriteDialogDismiss) {
            return initialState.builder().withAddFavouriteDialog(null).build()
        }

        if (partialState is PartialVS.FavoriteDialogSubmit) {
            return initialState.builder().withAddFavouriteDialog(null).withSuccessAddingToFavoritesMessage("Done")
                .build()
        }

        if (partialState is PartialVS.DoneMessageShowedOnce) {
            return initialState.builder().withAddFavouriteDialog(null).withSuccessAddingToFavoritesMessage(null)
                .build()
        }

        throw IllegalStateException("Unknown Partial!")
    }

    sealed class PartialVS {
        class Loading(val progress: Boolean) : PartialVS()
        class PullToRefresh(val channels: List<Source>, val progress: Boolean, val error: String?) : PartialVS()
        class ListShow(val channels: List<Source>, val progress: Boolean, val error: String?) : PartialVS()
        class EmptySourcesList() : PartialVS()
        class FavoriteDialogDismiss() : PartialVS()
        class FavoriteDialogSubmit() : PartialVS()
        class ChannelSelection(val channel: Source) : PartialVS()
        class DoneMessageShowedOnce() : PartialVS()
    }
}
