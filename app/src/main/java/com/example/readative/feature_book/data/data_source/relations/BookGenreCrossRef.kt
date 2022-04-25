package com.example.readative.feature_book.data.data_source.relations

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    primaryKeys = ["book_id", "genre_id"]
)
data class BookGenreCrossRef (
    @ColumnInfo(name = "book_id") val bookId: Long,
    @ColumnInfo(name = "genre_id") val genreId: Long
)