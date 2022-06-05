package com.example.reading

import com.example.framework.mvi.State
import com.example.model.local.result_entity.BookWithFullInfo
import com.example.reader.ReadativeBookContent
import com.example.reader.ReadativePage

sealed class ReadingState : State {
    object Loading: ReadingState()
    data class Loaded(
        val source: ReadativeBookContent,
        val information: BookWithFullInfo
        ): ReadingState()
    data class Error(val message: String): ReadingState()
}