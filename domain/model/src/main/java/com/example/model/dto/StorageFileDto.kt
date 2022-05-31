package com.example.model.dto

import android.os.Parcelable
import com.example.model.local.util.ArchiveFormat
import com.example.model.local.util.BookFormat
import kotlinx.parcelize.Parcelize

interface StorageFileDto {
    val name: String
    val bookCount: Int
}

@Parcelize
data class ArchiveDto(
    override val name: String,
    override val bookCount: Int,
    val archiveFormat: ArchiveFormat
) : Parcelable, StorageFileDto

@Parcelize
data class FolderDto(
    override val name: String,
    override val bookCount: Int
) : Parcelable, StorageFileDto

@Parcelize
data class StorageDestinationDto(
    override val name: String,
    val path: String,
    override val bookCount: Int
) : Parcelable, StorageFileDto