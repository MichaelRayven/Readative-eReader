package com.example.readative.persistance.entities

import androidx.annotation.NonNull
import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import androidx.room.ForeignKey.SET_NULL
import java.time.OffsetDateTime

@Entity(
    tableName = "books",
    indices = [
        Index(value = ["checksum"], unique = true),
        Index(value = ["author_id"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = Author::class,
            parentColumns = ["id"],
            childColumns = ["author_id"],
            onUpdate = CASCADE,
            onDelete = SET_NULL
        )
    ]
)
data class Book (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") override val id: Long,
    @ColumnInfo(name = "author_id") val authorId: Long? = null,
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