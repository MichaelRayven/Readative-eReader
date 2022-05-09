package com.example.model.dto

import android.os.Parcelable
import com.example.model.local.util.ReadingStatus
import kotlinx.parcelize.Parcelize
import java.time.OffsetDateTime

@Parcelize
data class BookDto(
    val id: Long,
    val title: String,
    val description: String,
    val bookCover: String,
    val readingProgress: Float,
    val readingStatus: ReadingStatus,
    val files: List<BookFileDto>,
    val series: List<SeriesDto>,
    val author: List<AuthorDto>,
    val publishingDate: OffsetDateTime,
    val addedDate: OffsetDateTime,
    val lastReadDate: OffsetDateTime
) : Parcelable
