package com.example.storage

import com.example.framework.mvi.Action
import com.example.model.dto.ArchiveDto
import com.example.model.dto.BasicBookDto
import com.example.model.dto.FolderDto

sealed class StorageAction : Action {
    data class FolderClicked(val path: String) : StorageAction()
    data class ArchiveClicked(val path: String) : StorageAction()
    data class BookClicked(val path: String) : StorageAction()
    data class FilesLoaded(
        val currentLocation: String,
        val books: List<BasicBookDto> = emptyList(),
        val folders: List<FolderDto> = emptyList(),
        val archives: List<ArchiveDto> = emptyList()
    ) : StorageAction()
    object InvalidDirectoryClicked : StorageAction()
    object LoadingFiles : StorageAction()
}