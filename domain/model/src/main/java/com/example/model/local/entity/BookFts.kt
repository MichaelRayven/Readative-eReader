package com.example.model.local.entity

import androidx.room.*

@Fts4(contentEntity = Book::class)
@Entity(tableName = "books_fts")
data class BookFts(
    @PrimaryKey
    @ColumnInfo(name = "rowid")
    val rowId: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "annotation") val description: String? = null
)
