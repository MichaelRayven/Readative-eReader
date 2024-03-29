package com.example.storage

import com.example.framework.mvi.State
import com.example.model.dto.*


sealed class StorageState : State {
    data class Home(
        val destinations: List<StorageDestinationDto>
    ) : StorageState()
    object Loading : StorageState()
    data class Loaded(
        val currentLocation: String,
        val books: List<BasicBookDto> = emptyList(),
        val folders: List<FolderDto> = emptyList(),
        val archives: List<ArchiveDto> = emptyList()
    ) : StorageState()
    data class Error(val message: String) : StorageState()
}
