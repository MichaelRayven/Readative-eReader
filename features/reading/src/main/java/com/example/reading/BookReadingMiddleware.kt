package com.example.reading

import com.example.framework.mvi.Middleware
import com.example.framework.mvi.Store
import com.example.usecase.reading.ReadingUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
                loadPages(store, action)
            }
            else -> {}
        }
    }

    private suspend fun loadPages(
        store: Store<ReadingState, ReadingAction>,
        action: ReadingAction.BookOpened
    ) {
        store.dispatch(ReadingAction.LoadingPages)
        withContext(Dispatchers.IO) {
            readingUseCases.getBookFiles(action.id).collect { files ->
                if (files.isNotEmpty()) {
                    val file = files.find { File(it.path).exists() }  ?: files[0]
                    val contents = readingUseCases.getBookContents(file.path, action.width, action.height)
                    val information = readingUseCases.getBook(action.id)
                    if (information != null) {
                        store.dispatch(ReadingAction.BookLoaded(contents, information))
                    } else {
                        store.dispatch(ReadingAction.OpenedInvalidBook)
                    }
                } else {
                    store.dispatch(ReadingAction.OpenedInvalidBook)
                }
            }
        }
    }
}