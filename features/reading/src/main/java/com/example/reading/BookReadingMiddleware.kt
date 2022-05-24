package com.example.reading

import com.example.framework.mvi.Middleware
import com.example.framework.mvi.Store
import com.example.model.local.entity.BookFile
import com.example.usecase.reading.ReadingUseCases
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.File

class BookReadingMiddleware(
    private val readingUseCases: ReadingUseCases
) : Middleware<ReadingState, ReadingAction> {
    override suspend fun process(
        action: ReadingAction,
        currentState: ReadingState,
        store: Store<ReadingState, ReadingAction>
    ) {
        when (action) {
            is ReadingAction.BookOpened -> {
                loadPages(store, currentState, action)
            }
            else -> {}
        }
    }

    private suspend fun loadPages(
        store: Store<ReadingState, ReadingAction>,
        currentState: ReadingState,
        action: ReadingAction.BookOpened
    ) {
        store.dispatch(ReadingAction.LoadingPages)
        readingUseCases.getBookFiles(action.id).onEach { files ->
            if (files.isNotEmpty()) {
                val file = files.find { File(it.path).exists() }  ?: files[0]
                val pages = readingUseCases.getBookPages(file.path)
                store.dispatch(ReadingAction.BookLoaded(pages))
            } else {
                store.dispatch(ReadingAction.OpenedInvalidBook)
            }
        }.launchIn(MainScope())
    }
}