package com.example.maxdo.jetrubytest.tabs.favoritesChannels

import com.example.maxdo.jetrubytest.core.entities.Source

class FavChannelsViewState(
    val channels: List<Source> = listOf(),
    val progress: Boolean = true,
    val sourceRelatedToDialog: Source? = null,
    val successRemovingFromFavoritesMessage: String? = null,
    val successRemovingFromFavoritesMessageShowedOnce: Boolean = false,
    val error: String? = null
) {
    fun builder() = Builder(
        channels,
        progress,
        sourceRelatedToDialog,
        successRemovingFromFavoritesMessage,
        successRemovingFromFavoritesMessageShowedOnce,
        error
    )

    class Builder(
        private var channels: List<Source>,
        private var progress: Boolean,
        private var sourceRelatedToDialog: Source?,
        private var successRemovingFromFavoritesMessage: String?,
        private var successRemovingFromFavoritesMessageShowedOnce: Boolean,
        private var error: String?
    ) {
        fun withChannels(channels: List<Source>) = apply { this.channels = channels }
        fun withProgress(progress: Boolean) = apply { this.progress = progress }
        fun withFavoriteRemoveDialog(sourceRelatedToDialog: Source?) =
            apply { this.sourceRelatedToDialog = sourceRelatedToDialog }

        fun withSuccessAddingToFavoritesMessage(successAddingToFavoritesMessage: String?) =
            apply { this.successRemovingFromFavoritesMessage = successAddingToFavoritesMessage }

        fun withError(error: String?) = apply { this.error = error }

        fun build() = FavChannelsViewState(
            channels, progress, sourceRelatedToDialog,
            successRemovingFromFavoritesMessage,
            successRemovingFromFavoritesMessageShowedOnce,
            error
        )
    }
}
