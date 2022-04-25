package com.example.readative.feature_book.data.data_source.models

import androidx.room.*
import com.example.readative.feature_book.data.data_source.models.Book

@Entity(
    tableName = "reviews",
    indices = [
        Index(value = ["book_id"], unique = true)
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
data class Review(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") override val id: Long,
    @ColumnInfo(name = "book_id") override val bookId: Long,
    @ColumnInfo(name = "rating") val rating: Int,
    @ColumnInfo(name = "text") val text: String
) : ReadativeEntity, BookIdEntity