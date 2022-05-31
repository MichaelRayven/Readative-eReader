package com.example.reading

import com.example.framework.mvi.Middleware
import com.example.framework.mvi.Store
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class CleanupMiddleware(
    val filesDir: File
) : Middleware<ReadingState, ReadingAction> {
    override suspend fun process(
        action: ReadingAction,
        currentState: ReadingState,
        store: Store<ReadingState, ReadingAction>
    ) {
        when (action) {
            is ReadingAction.BookClosed -> {
                withContext(Dispatchers.IO) {
                    filesDir.resolve("book").deleteRecursively()
                }
            }
            else -> {}
        }
    }
}