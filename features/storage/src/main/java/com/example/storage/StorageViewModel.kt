package com.example.storage

import android.os.Build
import android.os.Environment
import android.os.storage.StorageVolume
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.framework.mvi.Store
import com.example.usecase.files.StorageUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StorageViewModel @Inject constructor(
    storageUseCases: StorageUseCases
) : ViewModel() {

    private val store = Store(
        initialState = StorageState.Loading,
        reducer = StorageReducer(),
        middlewares = listOf(
            FileLoadingMiddleware(storageUseCases)
        )
    )

    val viewState: StateFlow<StorageState> = store.state

    init {
        folderClicked(Environment.getExternalStorageDirectory().absolutePath)
    }

    fun folderClicked(path: String) {
        val action = StorageAction.FolderClicked(path)

        viewModelScope.launch {
            store.dispatch(action)
        }
    }

    fun archiveClicked(path: String) {
        val action = StorageAction.ArchiveClicked(path)

        viewModelScope.launch {
            store.dispatch(action)
        }
    }
}