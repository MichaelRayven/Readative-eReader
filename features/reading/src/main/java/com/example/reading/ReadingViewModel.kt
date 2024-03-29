package com.example.reading

import android.speech.tts.TextToSpeech
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.framework.mvi.Store
import com.example.usecase.reading.ReadingUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ReadingViewModel @Inject constructor(
    bookReadingMiddleware: BookReadingMiddleware,
    cleanupMiddleware: CleanupMiddleware,
    ttsMiddleware: TTSMiddleware
) : ViewModel() {
    private val store = Store(
        initialState = ReadingState.Loading,
        reducer = ReadingReducer(),
        middlewares = listOf(
            bookReadingMiddleware,
            cleanupMiddleware,
            ttsMiddleware
        )
    )

    val actionFlow = MutableSharedFlow<ReadingAction>(1, 1)

    val viewState: StateFlow<ReadingState> = store.state

    init {
        viewModelScope.launch {
            actionFlow.collect { action ->
                viewModelScope.launch {
                    store.dispatch(action)
                }
            }
        }
    }

    fun bookOpened(id: Long, widthPx: Int, heightPx: Int) {
        val action = ReadingAction.BookOpened(id, widthPx, heightPx)

        viewModelScope.launch {
            store.dispatch(action)
        }
    }

    fun bookClosed() {
        val action = ReadingAction.BookClosed

        viewModelScope.launch {
            store.dispatch(action)
        }
    }
}