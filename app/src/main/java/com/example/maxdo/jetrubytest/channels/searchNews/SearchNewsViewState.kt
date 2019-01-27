package com.example.maxdo.jetrubytest.channels.searchNews

import com.example.maxdo.jetrubytest.core.entities.Article

class SearchNewsViewState(
    val articles: List<Article> = listOf(),
    val progress: Boolean = true,
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
        fun withArticles(articles: List<Article>) = apply { this.articles = articles }
        fun withProgress(progress: Boolean) = apply { this.progress = progress }
        fun withError(error: String?) = apply { this.error = error }

        fun build() = SearchNewsViewState(
            articles,
            progress,
            error
        )
    }

}
