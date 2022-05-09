package com.example.model.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey

@Fts4(contentEntity = Book::class)
@Entity(tableName = "books_fts")
data class BookFts(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "annotation") val description: String? = null
)
