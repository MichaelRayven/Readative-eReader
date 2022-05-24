package com.example.reading

import com.example.framework.mvi.State
import com.example.reader.ReadativePage

sealed class ReadingState : State {
    object Loading: ReadingState()
    data class Loaded(val pages: List<ReadativePage>): ReadingState()
    data class Error(val message: String): ReadingState()
}