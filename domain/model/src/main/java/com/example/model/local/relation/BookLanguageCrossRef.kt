package com.example.model.local.relation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
    indices = [
        Index(value = ["language_id"])
    ],
    primaryKeys = ["book_id", "language_id"]
)
data class BookLanguageCrossRef(
    @ColumnInfo(name = "book_id") val bookId: Long,
    @ColumnInfo(name = "language_id") val languageId: Long
)
