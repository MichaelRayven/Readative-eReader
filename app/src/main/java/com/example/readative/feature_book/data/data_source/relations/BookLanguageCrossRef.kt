package com.example.readative.feature_book.data.data_source.relations

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    primaryKeys = ["book_id", "language_id"]
)
data class BookLanguageCrossRef(
    @ColumnInfo(name = "book_id") val bookId: Long,
    @ColumnInfo(name = "language_id") val languageId: Long
)
