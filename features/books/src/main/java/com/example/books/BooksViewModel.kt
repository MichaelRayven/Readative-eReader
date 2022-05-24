package com.example.books

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.dto.extension.toBasicBookDto
import com.example.usecase.books.BookUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val bookUseCases: BookUseCases
): ViewModel() {
    private val _state = mutableStateOf(BooksState())
    val state: State<BooksState> = _state

    private var getBooksJob: Job? = null

    init {
        getBooks()
    }

    private fun getBooks() {
        getBooksJob?.cancel()
        getBooksJob = bookUseCases.getBooks()
            .onEach { bookList ->
                _state.value = state.value.copy(
                    books = bookList.map { it.toBasicBookDto() }
                )
            }
            .launchIn(viewModelScope)
    }
}