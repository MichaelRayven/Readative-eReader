package com.example.usecase.reading

data class ReadingUseCases (
    val getBookFiles: GetBookFilesByBookId,
    val getBookPages: GetBookPages
)