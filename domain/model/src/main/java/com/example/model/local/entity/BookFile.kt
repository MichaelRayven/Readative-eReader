package com.example.model.local.entity

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.example.model.local.util.BookFormat
import com.example.model.local.util.FileSize

@Entity(
    tableName = "files",
    indices = [
        Index(value = ["path"], unique = true),
        Index(value = ["book_id"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = Book::class,
            parentColumns = ["id"],
            childColumns = ["book_id"],
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