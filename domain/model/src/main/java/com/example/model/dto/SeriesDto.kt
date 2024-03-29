package com.example.model.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SeriesDto(
    val id: Long,
    val name: String,
    var part: Int? = null
) : Parcelable
