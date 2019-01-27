package com.example.maxdo.jetrubytest.core.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Article(
    val source: ArticleSource?,
    val author: String?,
    val title: String?,
    val description: String?,
    @PrimaryKey(autoGenerate = false)
    val url: String?,
    val urlToImage: String?,
    // The date and time that the article was published, in UTC (+000)
    val publishedAt: String?,
    val content: String?
)