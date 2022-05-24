package com.example.storage

import com.example.framework.mvi.Middleware
import com.example.framework.mvi.Store
import com.example.usecase.files.StorageUseCases
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.nio.file.Paths
import kotlin.io.path.exists

class BookAddingMiddleware(
    private val storageUseCases: StorageUseCases
) : Middleware<StorageState, StorageAction> {
    override suspend fun process(
        action: StorageAction,
        currentState: StorageState,
        store: Store<StorageState, StorageAction>
    ) {
        when (action) {
            is StorageAction.BookClicked -> {
                if (!Paths.get(action.path).exists()) {
                    store.dispatch(StorageAction.InvalidDirectoryClicked)
                    return
                }

                addBookToDatabase(store, currentState, action)
            }
            else -> {}
        }
    }

    private suspend fun addBookToDatabase(
        store: Store<StorageState, StorageAction>,
        currentState: StorageState,
        action: StorageAction.BookClicked
    ) {
        store.dispatch(StorageAction.LoadingFiles)


    }
}