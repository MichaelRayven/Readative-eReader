package com.example.reading.util

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ReadingProgress(
    val currentPage: Int,
    val pageCount: Int
) : Parcelable