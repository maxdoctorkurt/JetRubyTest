package com.example.maxdo.jetrubytest.core.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.maxdo.jetrubytest.core.entities.Source
import com.example.maxdo.jetrubytest.core.room.roomDao.SourceDAO

@Database(entities = [(Source::class)], version = 2)
abstract class RoomDB : RoomDatabase() {

    abstract val sourceDao: SourceDAO


}
