package com.example.readative.persistance.entities

enum class ReadingStatus(val storageKey: String) {
    UNREAD("unread"),
    READING("reading"),
    HAVE_READ("have_read")
}