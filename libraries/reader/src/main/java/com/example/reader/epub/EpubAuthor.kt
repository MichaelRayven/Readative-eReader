package com.example.reader.epub

import com.example.reader.ReadativeAuthor
import nl.siegmann.epublib.domain.Author

class EpubAuthor(
    private val source: Author,
): ReadativeAuthor {
    override val firstName: String
        get() {
            return source.lastname.substringBefore(' ')
        }
    override val middleName: String?
        get() {
            val result = source.lastname.substringAfter(' ', "")
            return if (result == "") null else result
        }
    override val lastName: String?
        get() {
            return source.firstname
        }

    override fun toAuthor(): com.example.model.local.entity.Author {
        return com.example.model.local.entity.Author(
            0,
            firstName,
            middleName,
            lastName
        )
    }
}