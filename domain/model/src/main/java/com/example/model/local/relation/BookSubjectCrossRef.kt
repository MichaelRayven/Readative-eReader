package com.example.model.local.relation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
    indices = [
        Index(value = ["subject_id"])
    ],
    primaryKeys = ["book_id", "subject_id"]
)
data class BookSubjectCrossRef (
    @ColumnInfo(name = "book_id") val bookId: Long,
    @ColumnInfo(name = "subject_id") val genreId: Long
)