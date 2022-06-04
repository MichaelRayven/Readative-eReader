package com.example.search

import com.example.framework.mvi.Action
import com.example.model.dto.BasicBookDto

sealed class SearchAction : Action {
    object LoadingStarted : SearchAction()
    object EmptyQueryEntered: SearchAction()
    data class QueryEntered(
        val query: String
    ) : SearchAction()
    data class Loaded(
        val data: List<BasicBookDto>
    ) : SearchAction()
}