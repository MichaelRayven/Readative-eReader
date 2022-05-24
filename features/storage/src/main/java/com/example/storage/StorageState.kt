package com.example.storage

import com.example.framework.mvi.State
import com.example.model.dto.ArchiveDto
import com.example.model.dto.BasicBookDto
import com.example.model.dto.FolderDto


sealed class StorageState : State {
    object Loading : StorageState()
    data class Loaded(
        val currentLocation: String,
        val books: List<BasicBookDto> = emptyList(),
        val folders: List<FolderDto> = emptyList(),
        val archives: List<ArchiveDto> = emptyList()
    ) : StorageState()
    data class Error(val message: String) : StorageState()
}
