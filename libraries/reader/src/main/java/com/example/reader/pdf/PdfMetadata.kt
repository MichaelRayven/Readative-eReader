package com.example.reader.pdf

import android.util.Log
import com.example.reader.ReadativeAuthor
import com.example.reader.ReadativeLanguage
import com.example.reader.ReadativeMetadata
import com.shockwave.pdfium.PdfDocument
import java.time.OffsetDateTime

class PdfMetadata(
    private val source: PdfDocument.Meta
): ReadativeMetadata {
    override val title: String
        get() = source.title
    override val description: String
        get() = ""
    override val authors: List<ReadativeAuthor>
        get() {
            val result = mutableListOf<ReadativeAuthor>()
            result.add(PdfAuthor(source.author))
            result.add(PdfAuthor(source.producer))
            result.add(PdfAuthor(source.creator))
            return result
        }
    override val subjects: List<String>
        get() = listOf(source.subject)
    override val languages: List<ReadativeLanguage>
        get() = emptyList()
    override val published: OffsetDateTime
        get() {
            Log.d("TESTING", source.creationDate)
            return OffsetDateTime.now()
        }
}