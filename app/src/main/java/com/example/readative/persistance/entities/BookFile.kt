package com.example.readative.persistance.entities

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(
    tableName = "files",
    indices = [
        Index(value = ["path"], unique = true)
    ],
    foreignKeys = [
        ForeignKey(
            entity = Book::class,
            parentColumns = ["id"],
            childColumns = ["book_id"],
            onUpdate = CASCADE,
            onDelete = CASCADE
        )
    ]
)
data class BookFile (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") override val id: Long,
    @ColumnInfo(name = "book_id") override val bookId: Long,
    @ColumnInfo(name = "path") val path: String,
    @ColumnInfo(name = "size") val size: FileSize,
    @ColumnInfo(name = "format") val format: BookFormat
) : ReadativeEntity, BookIdEntity