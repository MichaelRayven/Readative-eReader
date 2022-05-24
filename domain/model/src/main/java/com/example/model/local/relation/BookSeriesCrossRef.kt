package com.example.model.local.relation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
    indices = [
        Index(value = ["series_id"])
    ],
    primaryKeys = ["book_id", "series_id"]
)
data class BookSeriesCrossRef (
    @ColumnInfo(name = "book_id") val bookId: Long,
    @ColumnInfo(name = "series_id") val seriesId: Long,
    @ColumnInfo(name = "part") val partOfSeries: Int?
)