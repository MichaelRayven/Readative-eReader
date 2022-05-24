package com.example.usecase.app

import com.example.model.local.entity.Book
import com.example.repository.BookRepository

class AddBook(
    private val repository: BookRepository
) {
    suspend operator fun invoke(book: Book): Long {
        return repository.insert(book)
    }
}