package com.example.readative.feature_book.data.data_source.relations

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    primaryKeys = ["book_id", "author_id"]
)
data class BookAuthorCrossRef(
    @ColumnInfo(name = "book_id") val bookId: Long,
    @ColumnInfo(name = "author_id") val authorId: Long
)
