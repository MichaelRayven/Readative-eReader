package com.example.readative.feature_book.data.data_source.models

import androidx.room.*
import com.example.readative.feature_book.data.data_source.models.Book

@Entity(
    tableName = "bookmarks",
    indices = [
        Index(value = ["page"], unique = true)
    ],
    foreignKeys = [
        ForeignKey(
            entity = Book::class,
            parentColumns = ["id"],
            childColumns = ["book_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Bookmark(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") override val id: Long,
    @ColumnInfo(name = "book_id") override val bookId: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "page") val page: Int
) : ReadativeEntity, BookIdEntity