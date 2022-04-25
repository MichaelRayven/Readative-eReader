package com.example.readative.feature_book.data.data_source.models

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import androidx.room.ForeignKey.SET_NULL
import com.example.readative.feature_book.data.data_source.util.ReadingStatus
import java.time.OffsetDateTime

@Entity(
    tableName = "books",
    indices = [
        Index(value = ["checksum"], unique = true)
    ]
)
data class Book (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") override val id: Long,
    @ColumnInfo(name = "checksum") val checksum: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "annotation") val description: String? = null,
    @ColumnInfo(name = "book_cover") val coverPath: String,
    @ColumnInfo(name = "reading_status") val status: ReadingStatus = ReadingStatus.UNREAD,
    @ColumnInfo(name = "reading_progress") val progress: Float = 0f,
    @ColumnInfo(name = "published_date") val published: OffsetDateTime,
    @ColumnInfo(name = "added_date") val added: OffsetDateTime? = null,
    @ColumnInfo(name = "last_opened") val opened: OffsetDateTime? = null
) : ReadativeEntity