package com.example.maxdo.jetrubytest.core.room.roomDao

import androidx.room.*
import com.example.maxdo.jetrubytest.core.entities.Article

@Dao
interface ArticleDAO {
    @Query("SELECT * FROM Article")
    fun getAllArticles(): List<Article>?

    @Update
    fun update(source: Article)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(source: Article)

    @Delete
    fun delete(source: Article)

    @Query("DELETE FROM Article")
    fun removeAllArticles()
}