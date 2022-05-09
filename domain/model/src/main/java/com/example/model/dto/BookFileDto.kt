package com.example.model.dto

import android.os.Parcelable
import com.example.model.local.util.BookFormat
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookFileDto(
    val id: Long,
    val path: String,
    val fileSize: String,
    val bookFormat: BookFormat
) : Parcelable