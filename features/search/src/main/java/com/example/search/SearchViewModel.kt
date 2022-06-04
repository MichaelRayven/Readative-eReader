package com.example.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.framework.mvi.Store
import com.example.usecase.search.SearchUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    searchUseCases: SearchUseCases
) : ViewModel() {
    private val store = Store(
        initialState = SearchState.AwaitingInput,
        reducer = SearchReducer(),
        middlewares = listOf(
            BookSearchMiddleware(searchUseCases)
        )
    )

    val viewState: StateFlow<SearchState> = store.state

    fun queryEntered(query: String) {
        val action = SearchAction.QueryEntered(query)

        viewModelScope.launch {
            store.dispatch(action)
        }
    }
}