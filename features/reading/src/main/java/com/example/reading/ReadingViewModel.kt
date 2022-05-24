package com.example.reading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.framework.mvi.Store
import com.example.usecase.reading.ReadingUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadingViewModel @Inject constructor(
    readingUseCases: ReadingUseCases
) : ViewModel() {
    private val store = Store(
        initialState = ReadingState.Loading,
        reducer = ReadingReducer(),
        middlewares = listOf(
            BookReadingMiddleware(readingUseCases)
        )
    )

    val viewState: StateFlow<ReadingState> = store.state

    fun bookOpened(id: Long) {
        val action = ReadingAction.BookOpened(id)

        viewModelScope.launch {
            store.dispatch(action)
        }
    }
}