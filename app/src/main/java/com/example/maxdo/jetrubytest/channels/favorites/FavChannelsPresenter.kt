package com.example.maxdo.jetrubytest.channels.favorites

import com.example.maxdo.jetrubytest.core.Repository
import com.example.maxdo.jetrubytest.core.entities.Source
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class FavChannelsPresenter : MviBasePresenter<FavChannelsView, FavChannelsViewState>() {
    override fun bindIntents() {

        val initialState = FavChannelsViewState()

        val partialPullToRefresh: Observable<PartialVS> = intent(FavChannelsView::pullToRefreshFavChannelsIntent)
            .observeOn(Schedulers.io())
            .debounce(300, TimeUnit.MILLISECONDS)
            .flatMap {
                return@flatMap Repository.getFavSources().toObservable().cache()
            }
            .flatMap {
                val result = PartialVS.PullToRefresh(it, false, null) as PartialVS
                return@flatMap Observable.just(result)
            }
            .startWith(PartialVS.Loading(true))

        val partialFirstShow: Observable<PartialVS> = Repository.getFavSources().toObservable().cache()
            .flatMap {
                return@flatMap Observable.just(PartialVS.FirstShow(it, false, null) as PartialVS)
            }
            .onErrorResumeNext { throwable: Throwable ->
                Observable.just(
                    PartialVS.FirstShow(
                        listOf(), false,
                        throwable.message + throwable.localizedMessage
                    )
                )
            }

        val partialItemClick: Observable<PartialVS> = intent(FavChannelsView::getClickOnFavChannelIntent)
            .observeOn(Schedulers.io())
            .debounce(300, TimeUnit.MILLISECONDS)
            .map { PartialVS.ChannelSelection(it) }

        val partialDialogDismiss: Observable<PartialVS> =
            intent(FavChannelsView::getDismissFavChannelRemovingDialogIntent)
                .observeOn(Schedulers.io())
                .debounce(300, TimeUnit.MILLISECONDS)
                .map { PartialVS.FavoriteRemoveDialogDismiss() }

        val partialDialogSubmit: Observable<PartialVS> =
            intent(FavChannelsView::getSubmitRemovingFromFavChannelsDialogIntent)
                .observeOn(Schedulers.io())
                .debounce(300, TimeUnit.MILLISECONDS)
                .flatMap {
                    return@flatMap Repository.removeSourceFromFav(it).toObservable()
                }
                .flatMap {
                    // update after deleting
                    return@flatMap Repository.getFavSources().toObservable()
                }
                .map {
                    return@map PartialVS.FavoriteRemoveDialogSubmit(it)
                }

        val partialDoneShowedOnce: Observable<PartialVS> = intent(FavChannelsView::getNotifyDoneMessageShowedOnceIntent)
            .observeOn(Schedulers.io())
            .debounce(300, TimeUnit.MILLISECONDS)
            .map {
                return@map PartialVS.DoneMessageShowedOnce()
            }

        val observable: Observable<FavChannelsViewState> =
            partialPullToRefresh
                .mergeWith(partialFirstShow)
                .mergeWith(partialItemClick)
                .mergeWith(partialDialogDismiss)
                .mergeWith(partialDialogSubmit)
                .mergeWith(partialDoneShowedOnce)
                .scan(initialState, this::stateReducer)
                .startWith(
                    FavChannelsViewState(listOf(), true, null)
                ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(observable, FavChannelsView::render)
    }

    private fun stateReducer(initialState: FavChannelsViewState, partialState: PartialVS): FavChannelsViewState {

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
            return FavChannelsViewState(listOf(), false, null)
        }

        if (partialState is PartialVS.ChannelSelection) {
            return initialState.builder().withFavoriteRemoveDialog(partialState.channel).build()
        }

        if (partialState is PartialVS.FavoriteRemoveDialogDismiss) {
            return initialState.builder().withFavoriteRemoveDialog(null).build()
        }

        if (partialState is PartialVS.FavoriteRemoveDialogSubmit) {
            return initialState.builder().withFavoriteRemoveDialog(null).withSuccessAddingToFavoritesMessage("Done")
                .withChannels(partialState.updatedList)
                .build()
        }

        if (partialState is PartialVS.DoneMessageShowedOnce) {
            return initialState.builder().withFavoriteRemoveDialog(null).withSuccessAddingToFavoritesMessage(null)
                .build()
        }

        throw IllegalStateException("Unknown Partial!")
    }

    sealed class PartialVS {
        class Loading(val progress: Boolean) : PartialVS()
        class PullToRefresh(val channels: List<Source>, val progress: Boolean, val error: String?) : PartialVS()
        class FirstShow(val channels: List<Source>, val progress: Boolean, val error: String?) : PartialVS()
        class EmptySourcesList() : PartialVS()
        class FavoriteRemoveDialogDismiss() : PartialVS()
        class FavoriteRemoveDialogSubmit(val updatedList: List<Source>) : PartialVS()
        class ChannelSelection(val channel: Source) : PartialVS()
        class DoneMessageShowedOnce() : PartialVS()
    }


}
