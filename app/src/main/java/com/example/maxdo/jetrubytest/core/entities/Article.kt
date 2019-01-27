package com.example.maxdo.jetrubytest.core.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson


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