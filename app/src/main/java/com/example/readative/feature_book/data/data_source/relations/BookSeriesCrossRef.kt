package com.example.readative.feature_book.data.data_source.relations

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    primaryKeys = ["book_id", "series_id"]
)
data class BookSeriesCrossRef (
    @ColumnInfo(name = "book_id") val bookId: Long,
    @ColumnInfo(name = "series_id") val seriesId: Long,
    @ColumnInfo(name = "part") val partOfSeries: Int
)