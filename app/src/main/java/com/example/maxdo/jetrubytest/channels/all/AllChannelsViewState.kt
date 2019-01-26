package com.example.maxdo.jetrubytest.channels.all

import com.example.maxdo.jetrubytest.core.entities.Source

class AllChannelsViewState(
    val channels: List<Source> = listOf(),
    val progress: Boolean = true,
    val sourceRelatedToDialog: Source? = null,
    val successAddingToFavoritesMessage: String? = null,
    val successAddingToFavoritesMessageShowedOnce: Boolean = false,
    val error: String? = null
) {

    fun builder() = Builder(
        channels,
        progress,
        sourceRelatedToDialog,
        successAddingToFavoritesMessage,
        successAddingToFavoritesMessageShowedOnce,
        error
    )

    class Builder(
        private var channels: List<Source>,
        private var progress: Boolean,
        private var sourceRelatedToDialog: Source?,
        private var successAddingToFavoritesMessage: String?,
        private var successAddingToFavoritesMessageShowedOnce: Boolean,
        private var error: String?
    ) {
        fun withChannels(channels: List<Source>) = apply { this.channels = channels }
        fun withProgress(progress: Boolean) = apply { this.progress = progress }
        fun withAddFavouriteDialog(sourceRelatedToDialog: Source?) =
            apply { this.sourceRelatedToDialog = sourceRelatedToDialog }
        fun withSuccessAddingToFavoritesMessage(successAddingToFavoritesMessage: String?) =
            apply { this.successAddingToFavoritesMessage = successAddingToFavoritesMessage }

        fun withError(error: String?) = apply { this.error = error }
        fun build() = AllChannelsViewState(
            channels,
            progress,
            sourceRelatedToDialog,
            successAddingToFavoritesMessage,
            successAddingToFavoritesMessageShowedOnce,
            error
        )
    }

}
