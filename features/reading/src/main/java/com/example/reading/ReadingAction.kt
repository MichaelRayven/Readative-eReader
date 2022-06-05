package com.example.reading

import com.example.framework.mvi.Action
import com.example.model.local.entity.Book
import com.example.model.local.result_entity.BookWithFullInfo
import com.example.reader.ReadativeBookContent

sealed class ReadingAction : Action {
    data class BookOpened(
        val id: Long,
        val width: Int,
        val height: Int
    ) : ReadingAction()
    object BookClosed : ReadingAction()
    object LoadingPages : ReadingAction()
    data class BookLoaded(val source: ReadativeBookContent, val information: BookWithFullInfo) : ReadingAction()
    object OpenedInvalidBook : ReadingAction()

    data class TextToSpeechAdd(
        val text: String
        ): ReadingAction()
    object TextToSpeechReset: ReadingAction()
    object TextToSpeechPause: ReadingAction()
    object TextToSpeechResume: ReadingAction()
    object TextToSpeechStart: ReadingAction()
    data class TextToSpeechNextClicked(
        val count: Int
    ): ReadingAction()
    data class TextToSpeechPrevClicked(
        val count: Int
    ): ReadingAction()
}