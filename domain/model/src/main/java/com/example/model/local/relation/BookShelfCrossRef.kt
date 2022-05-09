package com.example.model.local.relation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
    indices = [
        Index(value = ["shelf_id"])
    ],
    primaryKeys = ["book_id", "shelf_id"]
)
data class BookShelfCrossRef(
    @ColumnInfo(name = "book_id") val bookId: Long,
    @ColumnInfo(name = "shelf_id") val shelfId: Long
)
