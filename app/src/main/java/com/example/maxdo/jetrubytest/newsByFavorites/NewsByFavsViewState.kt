package com.example.maxdo.jetrubytest.newsByFavorites

import com.example.maxdo.jetrubytest.core.entities.Article

class NewsByFavsViewState(
    val articles: List<Article> = listOf(),
    val progress: Boolean = false,
    val error: String? = null
) {
    fun builder() = Builder(
        articles,
        progress,
        error
    )

    class Builder(
        private var articles: List<Article>,
        private var progress: Boolean,
        private var error: String?
    ) {
        fun withArticles(channels: List<Article>) = apply { this.articles = channels }
        fun withProgress(progress: Boolean) = apply { this.progress = progress }
        fun withError(error: String?) = apply { this.error = error }
        fun build() = NewsByFavsViewState(
            articles,
            progress,
            error
        )
    }

}
