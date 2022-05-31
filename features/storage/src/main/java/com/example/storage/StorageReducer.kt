package com.example.storage

import com.example.framework.mvi.Reducer
import com.example.model.dto.StorageDestinationDto

class StorageReducer : Reducer<StorageState, StorageAction> {
    override fun reduce(currentState: StorageState, action: StorageAction): StorageState {
        return when (action) {
            is StorageAction.HomeClicked -> {
                stateWithStorageVolumes(action)
            }
            is StorageAction.LoadingFiles -> {
                stateWhenGatheringFiles()
            }
            is StorageAction.InvalidDirectoryClicked -> {
                stateWithInvalidDirectoryError()
            }
            is StorageAction.FilesLoaded -> {
                stateAfterGatheringSuccessful(action)
            }
            else -> currentState
        }
    }

    private fun stateWithStorageVolumes(action: StorageAction.HomeClicked): StorageState {
        return StorageState.Home(
            action.volumes.map {
            StorageDestinationDto(
            it.absolutePath.substringAfter("/storage/"),
            it.absolutePath,
            0
        ) })
    }

    private fun stateAfterGatheringSuccessful(
        action: StorageAction.FilesLoaded
    ) = StorageState.Loaded(
        action.currentLocation,
        action.books,
        action.folders,
        action.archives
    )

    private fun stateWithInvalidDirectoryError() = StorageState.Error("Invalid directory path")

    private fun stateWhenGatheringFiles() = StorageState.Loading

}