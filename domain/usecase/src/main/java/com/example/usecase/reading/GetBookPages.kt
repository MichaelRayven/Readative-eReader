package com.example.usecase.reading

import android.util.Log
import com.example.reader.ReadativeBookReader
import com.example.reader.ReadativePage
import java.io.File

class GetBookPages(
    private val bookReader: ReadativeBookReader
) {
    operator fun invoke(path: String, screenWidth: Int, screenHeight: Int): List<ReadativePage> {
        val pages = mutableListOf<ReadativePage>()
        val book = bookReader.readBook(File(path))!!
        for (i in 0 until book.pageCount) {
            book.getPage(i, screenWidth, screenHeight).let { pages.add(it) }
        }
        book.close()
        return pages
    }
}