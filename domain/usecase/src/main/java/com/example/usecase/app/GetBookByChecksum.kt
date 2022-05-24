package com.example.usecase.app

import com.example.model.local.entity.Book
import com.example.repository.BookRepository
import kotlinx.coroutines.flow.Flow

class GetBookByChecksum(
    private val repository: BookRepository
) {
    suspend operator fun invoke(checksum: String): Book? {
        return repository.getByChecksum(checksum)
    }
}