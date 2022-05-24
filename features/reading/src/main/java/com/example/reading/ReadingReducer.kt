package com.example.reading

import com.example.framework.mvi.Reducer

class ReadingReducer : Reducer<ReadingState, ReadingAction> {
    override fun reduce(currentState: ReadingState, action: ReadingAction): ReadingState {
        return when (action) {
            is ReadingAction.LoadingPages -> {
                stateWhenLoadingPages()
            }
            is  ReadingAction.BookLoaded -> {
                stateAfterLoadingPagesSuccessful(action)
            }
            else -> currentState
        }
    }

    private fun stateAfterLoadingPagesSuccessful(
        action: ReadingAction.BookLoaded
    ) = ReadingState.Loaded(action.pages)

    private fun stateWhenLoadingPages() = ReadingState.Loading
}