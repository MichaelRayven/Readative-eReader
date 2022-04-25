package com.example.readative.feature_book.data.data_source.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import com.example.readative.feature_book.data.data_source.models.Book

@Fts4(contentEntity = Book::class)
@Entity(tableName = "books_fts")
data class BookFts(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "annotation") val description: String? = null
)
