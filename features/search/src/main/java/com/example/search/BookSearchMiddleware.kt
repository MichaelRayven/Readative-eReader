package com.example.search

import com.example.framework.mvi.Middleware
import com.example.framework.mvi.Store
import com.example.model.dto.extension.toBasicBookDto
import com.example.usecase.search.SearchUseCases
import kotlinx.coroutines.flow.collect

class BookSearchMiddleware(
    private val searchUseCases: SearchUseCases
) : Middleware<SearchState, SearchAction> {
    override suspend fun process(
        action: SearchAction,
        currentState: SearchState,
        store: Store<SearchState, SearchAction>
    ) {
        when(action) {
            is SearchAction.QueryEntered -> {
                getSearchResults(action, store)
            }
            else -> {}
        }
    }

    private suspend fun getSearchResults(
        action: SearchAction.QueryEntered,
        store: Store<SearchState, SearchAction>
    ) {
        store.dispatch(SearchAction.LoadingStarted)

        val escapedQuotesQuery = action.query
            .replace(Regex.fromLiteral("\""), "\"\"")
            .replace(Regex.fromLiteral("\'"), "\'\'")

        if(action.query.isNotEmpty()) {
            searchUseCases.searchBooks("*\"$escapedQuotesQuery\"*").collect { books ->
                val newAction = SearchAction.Loaded(books.map { it.toBasicBookDto() })
                store.dispatch(newAction)
            }
        } else {
            store.dispatch(SearchAction.EmptyQueryEntered)
        }
    }
}