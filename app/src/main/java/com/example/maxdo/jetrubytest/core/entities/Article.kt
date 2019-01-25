package com.example.maxdo.jetrubytest.core.entities

class Article(
    val source: ArticleSource?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    // The date and time that the article was published, in UTC (+000)
    val publishedAt: String?,
    val content: String?
)