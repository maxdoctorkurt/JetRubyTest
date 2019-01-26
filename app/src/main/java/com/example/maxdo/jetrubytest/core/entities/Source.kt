package com.example.maxdo.jetrubytest.core.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Source(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String?,
    val description: String?,
    val url: String?,
    val category: String?,
    val language: String?,
    val country: String?
)