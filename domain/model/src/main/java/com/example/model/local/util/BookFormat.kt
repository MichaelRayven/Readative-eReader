package com.example.model.local.util

enum class BookFormat(
    val extension: String,
    val bookFormat: String,
    val archiveFormat: ArchiveFormat? = null
) {
    PDF(".pdf", "PDF"),
    FBZ(".fbz", "FB2", ArchiveFormat.ZIP),
    DOC(".doc", "DOC"),
    DOCX(".docx", "DOCX"),
    RTF(".rtf", "RTF"),
    MOBI(".mobi", "MOBI"),
    TXT(".txt", "TXT"),
    FB2(".fb2", "FB2"),
    EPUB(".epub", "EPUB");
}