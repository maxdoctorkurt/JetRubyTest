package com.example.maxdo.jetrubytest.core.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.maxdo.jetrubytest.core.entities.Article
import com.example.maxdo.jetrubytest.core.entities.ArticleSourceConverter
import com.example.maxdo.jetrubytest.core.entities.Source
import com.example.maxdo.jetrubytest.core.room.roomDao.ArticleDAO
import com.example.maxdo.jetrubytest.core.room.roomDao.SourceDAO

@Database(entities = [Source::class, Article::class], version = 4)
@TypeConverters(ArticleSourceConverter::class)
abstract class RoomDB : RoomDatabase() {
    abstract val sourceDao: SourceDAO
    abstract val articleDao: ArticleDAO
}
