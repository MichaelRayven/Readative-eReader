package com.example.usecase.reading

import com.example.reader.ReadativeBookReader
import com.example.reader.ReadativePage
import java.io.File

class GetBookPages(
    private val bookReader: ReadativeBookReader
) {
    operator fun invoke(path: String): List<ReadativePage> {
        val pages = mutableListOf<ReadativePage>()
        val book = bookReader.readBook(File(path)) ?: return emptyList()
        for (i in 0 until book.pageCount()) {
            book.getPage(i).let { pages.add(it) }
        }
        book.close()
        return pages
    }
}