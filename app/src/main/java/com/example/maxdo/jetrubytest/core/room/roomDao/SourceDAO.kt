package com.example.maxdo.jetrubytest.core.room.roomDao

import androidx.room.*
import com.example.maxdo.jetrubytest.core.entities.Source

@Dao
interface SourceDAO {

    @Query("SELECT * FROM Source")
    fun getAllSources(): List<Source>?

    @Update
    fun update(source: Source)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(source: Source)

    @Delete
    fun delete(source: Source)
}