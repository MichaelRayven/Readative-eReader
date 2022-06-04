package com.example.search

import com.example.framework.mvi.Reducer

class SearchReducer : Reducer<SearchState, SearchAction> {
    override fun reduce(currentState: SearchState, action: SearchAction): SearchState {
        return when(action) {
            is SearchAction.LoadingStarted -> {
                stateWhenLoading()
            }
            is SearchAction.Loaded -> {
                stateWhenLoaded(action)
            }
            is SearchAction.EmptyQueryEntered -> {
                stateWhenQueryIsEmpty()
            }
            else -> currentState
        }
    }

    private fun stateWhenQueryIsEmpty(): SearchState = SearchState.AwaitingInput

    private fun stateWhenLoading(): SearchState.Loading = SearchState.Loading

    private fun stateWhenLoaded(action: SearchAction.Loaded): SearchState {
        return if (action.data.isEmpty()) {
            SearchState.NoResult
        } else {
            SearchState.Loaded(action.data)
        }
    }
}