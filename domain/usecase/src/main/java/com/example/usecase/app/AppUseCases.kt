package com.example.usecase.app

data class AppUseCases(
    val addAuthor: AddAuthor,
    val addBook: AddBook,
    val addBookFile: AddBookFile,
    val getBookByChecksum: GetBookByChecksum,
    val getBookFileByPath: GetBookFileByPath
)