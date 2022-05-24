package com.example.reader

import java.time.OffsetDateTime

interface ReadativeMetadata {
    val title: String
    val description: String
    val authors: List<ReadativeAuthor>
    val subjects: List<String>
    val languages: List<ReadativeLanguage>
    val published: OffsetDateTime
}