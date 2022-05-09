package com.example.model.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthorDto(
    val id: Long,
    val firstName: String,
    val middleName: String?,
    val lastName: String?
) : Parcelable
