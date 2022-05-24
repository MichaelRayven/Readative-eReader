package com.example.reader.pdf

import com.example.reader.ReadativeAuthor
import nl.siegmann.epublib.domain.Author

class PdfAuthor(
    val source: String,
): ReadativeAuthor {
    override val firstName: String
        get() {
            val array = source.split(' ')
            return if (array.isNotEmpty()) array[0] else ""
        }
    override val middleName: String?
        get() {
            val array = source.split(' ')
            return if (array.size > 1) array[1] else null
        }
    override val lastName: String?
        get() {
            val array = source.split(' ')
            return if (array.size > 2) array[2] else null
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