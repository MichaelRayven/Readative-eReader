package com.example.usecase.common

import com.example.model.local.result_entity.BookWithBasicInfo
import com.example.model.local.result_entity.BookWithFullInfo
import com.example.repository.BookRepository

class GetBook(
    private val repository: BookRepository
) {
    suspend operator fun invoke(id: Long): BookWithFullInfo? {
        return repository.getByIdWithFullInfo(id)
    }
}