package com.example.usecase.books

import com.example.usecase.common.GetBook

data class BookUseCases (
    val getBooks: GetBooks,
    val getBook: GetBook
)