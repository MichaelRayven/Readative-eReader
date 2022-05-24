package com.example.usecase.app

import com.example.model.local.entity.Author
import com.example.model.local.relation.BookAuthorCrossRef
import com.example.repository.AuthorRepository
import com.example.repository.BookAuthorCrossRefRepository

class AddAuthor(
    private val repository: AuthorRepository,
    private val crossRefRepository: BookAuthorCrossRefRepository
) {
    suspend operator fun invoke(author: Author): Long {
        return repository.insert(author)
    }

    suspend operator fun invoke(author: Author, bookId: Long): Long {
        val authorId = invoke(author)
        crossRefRepository.insert(BookAuthorCrossRef(bookId, authorId))
        return authorId
    }
}