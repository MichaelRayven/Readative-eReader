package com.example.books

import com.example.model.dto.BasicBookDto

data class BooksState(
    val books: List<BasicBookDto> = emptyList()
)