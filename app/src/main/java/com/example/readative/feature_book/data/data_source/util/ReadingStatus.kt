package com.example.readative.feature_book.data.data_source.util

enum class ReadingStatus(val storageKey: String) {
    UNREAD("unread"),
    READING("reading"),
    HAVE_READ("have_read")
}