package com.example.usecase.search

import com.example.repository.BookFtsRepository

data class SearchUseCases(
    val searchBooks: SearchForBooksWithBasicInfo
)