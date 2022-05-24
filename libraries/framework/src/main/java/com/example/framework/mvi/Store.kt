package com.example.framework.mvi

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

open class Store<S: State, A: Action>(
    initialState: S,
    private val reducer: Reducer<S, A>,
    private val middlewares: List<Middleware<S, A>> = emptyList(),
) {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    private val currentState: S
        get() = _state.value

    suspend fun dispatch(action: A) {
        middlewares.forEach { middleware ->
            middleware.process(action, currentState, this)
        }

        val newState = reducer.reduce(currentState, action)
        _state.value = newState
    }
}