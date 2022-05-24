package com.example.reading

import com.example.framework.mvi.Action
import com.example.reader.ReadativePage

sealed class ReadingAction : Action {
    data class BookOpened(val id: Long): ReadingAction()
    object LoadingPages: ReadingAction()
    data class BookLoaded(val pages: List<ReadativePage>): ReadingAction()
    object OpenedInvalidBook: ReadingAction()
}