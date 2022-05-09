package com.example.model.local.util

enum class ReadingStatus(val storageKey: String) {
    UNREAD("unread"),
    READING("reading"),
    HAVE_READ("have_read")
}