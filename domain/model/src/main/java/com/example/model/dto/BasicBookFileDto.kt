package com.example.model.dto

import android.os.Parcelable
import com.example.model.local.util.BookFormat
import com.example.model.local.util.FileSize
import kotlinx.parcelize.Parcelize

@Parcelize
data class BasicBookFileDto(
    val fileSize: String,
    val bookFormat: BookFormat
) : Parcelable
