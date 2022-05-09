package com.example.model.local.entity

import android.graphics.Color
import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(
    tableName = "quotes",
    indices = [
        Index(value = ["book_id"])
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
data class Quote (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") override val id: Long,
    @ColumnInfo(name = "book_id") override val bookId: Long,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "comment") val comment: String,
    @ColumnInfo(name = "page") val page: Int,
    @ColumnInfo(name = "start_index") val start: Int,
    @ColumnInfo(name = "end_index") val end: Int,
    @ColumnInfo(name = "highlight_color") val highlight: Color = Color.valueOf(0x44000000)
) : ReadativeEntity, BookIdEntity
