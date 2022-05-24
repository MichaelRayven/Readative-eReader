package com.example.reader

import com.example.model.local.entity.Author

interface ReadativeAuthor {
    val firstName: String
    val middleName: String?
    val lastName: String?

    fun toAuthor(): Author
}