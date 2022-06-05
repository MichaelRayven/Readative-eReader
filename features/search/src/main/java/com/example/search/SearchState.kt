package com.example.search

import com.example.framework.mvi.State
import com.example.model.dto.BasicBookDto

sealed class SearchState : State {
    object Loading : SearchState()
    object NoResult : SearchState()
    object AwaitingInput : SearchState()
    data class Loaded(
        val data: List<BasicBookDto>
    ) : SearchState()
}