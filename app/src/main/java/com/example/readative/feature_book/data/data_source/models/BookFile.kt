package com.example.readative.feature_book.data.data_source.models

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.example.readative.feature_book.data.data_source.models.Book
import com.example.readative.feature_book.data.data_source.util.BookFormat
import com.example.readative.feature_book.data.data_source.util.FileSize

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
    @ColumnInfo(name = "format") val format: BookFormat,
    @ColumnInfo(name = "primary_status") val isPrimary: Boolean = false
) : ReadativeEntity, BookIdEntity