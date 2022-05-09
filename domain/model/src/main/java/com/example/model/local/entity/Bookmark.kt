package com.example.model.local.entity

import androidx.room.*

@Entity(
    tableName = "bookmarks",
    indices = [
        Index(value = ["page"], unique = true),
        Index(value = ["book_id"])
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
