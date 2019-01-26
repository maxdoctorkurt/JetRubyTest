package com.example.maxdo.jetrubytest.channels.favorites

import com.example.maxdo.jetrubytest.core.entities.Source

class FavChannelsViewState (
    val channels: List<Source> = listOf(),
    val progress: Boolean = true,
    val removeFromFavChannelsDialogContent: String? = null,
    val error: String? = null
    ) {
        fun builder() = Builder(channels, progress, removeFromFavChannelsDialogContent, error)

        class Builder(private var channels: List<Source>, private var progress: Boolean, private var fromFavRemovingDialogContent: String?, private var error: String?) {
            fun withChannels(channels: List<Source>) = apply { this.channels = channels }
            fun withProgress(progress: Boolean) = apply { this.progress = progress }
            fun withFavoriteRemoveDialog(dialog: String?) = apply { this.fromFavRemovingDialogContent = dialog }
            fun withError(error: String?) = apply { this.error = error }
            fun build() = FavChannelsViewState(channels, progress, error)
        }
}
