package com.example.storage

import com.example.framework.mvi.Middleware
import com.example.framework.mvi.Store
import com.example.usecase.files.StorageUseCases
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.nio.file.Paths
import kotlin.io.path.exists

class FileLoadingMiddleware(
    private val storageUseCases: StorageUseCases
) : Middleware<StorageState, StorageAction> {
    override suspend fun process(
        action: StorageAction,
        currentState: StorageState,
        store: Store<StorageState, StorageAction>
    ) {
        when (action) {
            is StorageAction.FolderClicked -> {
                if (!Paths.get(action.path).exists()) {
                    store.dispatch(StorageAction.InvalidDirectoryClicked)
                    return
                }

                gatherFiles(store, action)
            }
            else -> {}
        }
    }

    private suspend fun gatherFiles(
        store: Store<StorageState, StorageAction>,
        action: StorageAction.FolderClicked
    ) {
        store.dispatch(StorageAction.LoadingFiles)

        storageUseCases.getFiles(action.path).let { ( books, folders, archives ) ->
            store.dispatch(StorageAction.FilesLoaded(
                action.path, books, folders, archives
            ))
        }
    }
}