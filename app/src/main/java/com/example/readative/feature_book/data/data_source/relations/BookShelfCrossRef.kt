package com.example.readative.feature_book.data.data_source.relations

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    primaryKeys = ["book_id", "shelf_id"]
)
data class BookShelfCrossRef(
    @ColumnInfo(name = "book_id") val bookId: Long,
    @ColumnInfo(name = "shelf_id") val shelfId: Long
)
