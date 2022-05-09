package com.example.model.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BasicAuthorDto(
    val shortenedFullName: String
) : Parcelable
