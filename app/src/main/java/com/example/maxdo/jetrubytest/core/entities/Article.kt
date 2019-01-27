package com.example.maxdo.jetrubytest.core.entities

import androidx.room.*
import com.google.gson.Gson
import java.util.Arrays.asList


@Entity
data class Article(

    val source: ArticleSource?,
    val author: String?,
    val title: String?,
    val description: String?,
    @PrimaryKey(autoGenerate = false)
    val url: String,
    val urlToImage: String?,
    // The date and time that the article was published, in UTC (+000)
    val publishedAt: String?,
    val content: String?
)


/*
*
*   val source: ArticleSource?,
    val author: String? = null,
    val title: String? = null,
    val description: String? = null,
    @PrimaryKey(autoGenerate = false)
    val url: String,
    val urlToImage: String? = null,
    // The date and time that the article was published, in UTC (+000)
    val publishedAt: String? = null,
    val content: String? = null
*
* */


class ArticleSourceConverter {

    companion object {
        @TypeConverter
        @JvmStatic
        fun fromArticleSource(source: ArticleSource?): String {
            val result = Gson().toJson(source)
            return result
        }

        @TypeConverter
        @JvmStatic
        fun toArticleSource(data: String): ArticleSource? {
            val result = Gson().fromJson(data, ArticleSource::class.java)
            return result
        }

    }


}