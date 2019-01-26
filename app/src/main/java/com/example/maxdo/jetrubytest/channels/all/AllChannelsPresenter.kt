package com.example.maxdo.jetrubytest.channels.all

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
            .observeOn(Schedulers.io())
            .debounce(300, TimeUnit.MILLISECONDS)
            .flatMap {
                return@flatMap Repository.sources().toObservable().cache()
            }
            .flatMap {
                if (it.sources != null) {
                    val result = PartialVS.PullToRefresh(it.sources, false, null) as PartialVS
                    return@flatMap Observable.just(result)
                }
                return@flatMap Observable.just(PartialVS.PullToRefresh(listOf(), false, "Error!"))
            }
            .startWith(PartialVS.Loading(true))

        val partialFirstShow: Observable<PartialVS> = Repository.sources().toObservable().cache()
            .flatMap {
                if (it.sources != null)
                    return@flatMap Observable.just(PartialVS.FirstShow(it.sources, false, null) as PartialVS)
                return@flatMap Observable.just(PartialVS.FirstShow(listOf(), false, "No sources!") as PartialVS)
            }
            .onErrorResumeNext { throwable: Throwable ->
                Observable.just(
                    PartialVS.FirstShow(
                        listOf(), false,
                        throwable.message + throwable.localizedMessage
                    )
                )
            }

        val partialItemClick: Observable<PartialVS> = intent(AllChannelsView::getClickOnChannelIntent)
            .observeOn(Schedulers.io())
            .debounce(300, TimeUnit.MILLISECONDS)
            .map { PartialVS.ChannelSelection(it) }

        val partialDialogDismiss: Observable<PartialVS> = intent(AllChannelsView::getDismissFavChannelDialogIntent)
            .observeOn(Schedulers.io())
            .debounce(300, TimeUnit.MILLISECONDS)
            .map { PartialVS.FavoriteDialogDismiss() }

        val partialDialogSubmit: Observable<PartialVS> = intent(AllChannelsView::getSubmitAddingToFavChannelsDialogIntent)
            .observeOn(Schedulers.io())
            .debounce(300, TimeUnit.MILLISECONDS)
            .flatMap {
                return@flatMap Repository.addSourceToFav(it).toObservable()
            }
            .map {
                return@map PartialVS.FavoriteDialogSubmit()
            }

        val partialDoneShowedOnce: Observable<PartialVS> = intent(AllChannelsView::getNotifyDoneMessageShowedOnceIntent)
            .observeOn(Schedulers.io())
            .debounce(300, TimeUnit.MILLISECONDS)
            .map {
                return@map PartialVS.DoneMessageShowedOnce()
            }


        val observable: Observable<AllChannelsViewState> =
            partialPull
                .mergeWith(partialFirstShow)
                .mergeWith(partialItemClick)
                .mergeWith(partialDialogDismiss)
                .mergeWith(partialDialogSubmit)
                .mergeWith(partialDoneShowedOnce)
                .scan(initialState, this::stateReducer)
                .startWith(
                    AllChannelsViewState(
                        listOf(),
                        true,
                        null,
                        null)
                ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(observable, AllChannelsView::render)

    }

    private fun stateReducer(initialState: AllChannelsViewState, partialState: PartialVS): AllChannelsViewState {

        if (partialState is PartialVS.Loading) {
            return initialState.builder().withChannels(listOf()).withError(null).withProgress(true)
                .build()
        }

        if (partialState is PartialVS.FirstShow) {
            return initialState.builder().withChannels(partialState.channels).withError(null).withProgress(false)
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
        class FirstShow(val channels: List<Source>, val progress: Boolean, val error: String?) : PartialVS()
        class EmptySourcesList() : PartialVS()
        class FavoriteDialogDismiss() : PartialVS()
        class FavoriteDialogSubmit() : PartialVS()
        class ChannelSelection(val channel: Source) : PartialVS()
        class DoneMessageShowedOnce() : PartialVS()
    }
}
