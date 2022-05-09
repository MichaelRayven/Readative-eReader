package com.example.model.local.relation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
    indices = [
        Index(value = ["author_id"])
    ],
    primaryKeys = ["book_id", "author_id"]
)
data class BookAuthorCrossRef(
    @ColumnInfo(name = "book_id") val bookId: Long,
    @ColumnInfo(name = "author_id") val authorId: Long
)
