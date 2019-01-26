package com.example.maxdo.jetrubytest.channels.all

import com.example.maxdo.jetrubytest.core.entities.Source

class AllChannelsViewState(
    val channels: List<Source> = listOf(),
    val progress: Boolean = true,
    val addToFavChannelsDialogContent: String? = null,
    val error: String? = null
) {

    fun builder() = Builder(channels, progress, addToFavChannelsDialogContent, error)

    class Builder(private var channels: List<Source>, private var progress: Boolean, private var favDialogContent: String?, private var error: String?) {
        fun withChannels(channels: List<Source>) = apply { this.channels = channels }
        fun withProgress(progress: Boolean) = apply { this.progress = progress }
        fun withFavouriteDialog(dialog: String?) = apply { this.favDialogContent = dialog }
        fun withError(error: String?) = apply { this.error = error }
        fun build() = AllChannelsViewState(channels, progress, error)
    }

}
