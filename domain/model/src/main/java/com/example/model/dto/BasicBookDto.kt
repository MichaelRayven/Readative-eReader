package com.example.model.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BasicBookDto(
    val id: Long,
    val title: String,
    val bookCover: String,
    val readingProgress: Float,
    val file: BasicBookFileDto?,
    val series: List<SeriesDto>,
    val authors: List<BasicAuthorDto>
) : Parcelable
