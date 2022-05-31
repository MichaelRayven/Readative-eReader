package com.example.reader.epub

import com.example.reader.ReadativeAuthor
import com.example.reader.ReadativeLanguage
import com.example.reader.ReadativeMetadata
import nl.siegmann.epublib.domain.Date
import nl.siegmann.epublib.domain.Metadata
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class EpubMetadata(
    private val source: Metadata
) : ReadativeMetadata {
    override val title: String
        get() = source.firstTitle
    override val description: String
        get() = if (source.descriptions.size > 0) source.descriptions[0] else ""
    override val authors: List<ReadativeAuthor>
        get() {
            return source.authors.map { EpubAuthor(it) }
        }
    override val subjects: List<String>
        get() = source.subjects
    override val languages: List<ReadativeLanguage>
        get() {
            return listOf(ReadativeLanguage(source.language))
        }
    override val published: OffsetDateTime
        get() {
            var date = source.dates.firstOrNull { it.event == Date.Event.PUBLICATION }
            if (date == null) {
                date = source.dates.firstOrNull { it.event == Date.Event.MODIFICATION }
            }
            if (date == null) {
                date = source.dates.firstOrNull { it.event == Date.Event.CREATION }
            }
            if (date == null) {
                date = source.dates.firstOrNull { it.event == Date.Event.CREATION }
            }

            return if (date == null) {
                OffsetDateTime.now()
            } else {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
                OffsetDateTime.parse(date.value, formatter)
            }
        }
}