package com.example.usecase.common

import com.example.model.local.entity.Book
import com.example.model.local.result_entity.BookWithFullInfo
import com.example.repository.AuthorRepository
import com.example.repository.BookRepository

class UpdateBook(
    private val bookRepository: BookRepository
) {
    suspend operator fun invoke(book: Book) {
        bookRepository.update(book)
    }
}