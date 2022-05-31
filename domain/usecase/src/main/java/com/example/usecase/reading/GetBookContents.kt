package com.example.usecase.reading

import com.example.reader.ReadativeBookContent
import com.example.reader.ReadativeBookReader
import com.example.reader.ReadativePage
import java.io.File

class GetBookContents(
    private val bookReader: ReadativeBookReader
) {
    operator fun invoke(path: String, screenWidth: Int, screenHeight: Int): ReadativeBookContent {
        val book = bookReader.readBook(File(path))!!
        return book.getBookContents(screenWidth, screenHeight)
    }
}