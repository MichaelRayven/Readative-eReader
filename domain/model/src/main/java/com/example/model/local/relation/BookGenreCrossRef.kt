package com.example.model.local.relation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
    indices = [
        Index(value = ["genre_id"])
    ],
    primaryKeys = ["book_id", "genre_id"]
)
data class BookGenreCrossRef (
    @ColumnInfo(name = "book_id") val bookId: Long,
    @ColumnInfo(name = "genre_id") val genreId: Long
)