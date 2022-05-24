package com.example.framework.mvi

interface Reducer<S: State, A: Action> {
    fun reduce(currentState: S, action: A): S
}