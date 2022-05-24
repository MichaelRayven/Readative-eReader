package com.example.usecase.books

import com.example.model.local.result_entity.BookWithFullInfo
import com.example.repository.AuthorRepository
import com.example.repository.BookRepository

class UpdateBook(
    private val bookRepository: BookRepository,
    private val authorRepository: AuthorRepository
) {
    suspend operator fun invoke(book: BookWithFullInfo) {
        bookRepository.update(book.book)
    }
}