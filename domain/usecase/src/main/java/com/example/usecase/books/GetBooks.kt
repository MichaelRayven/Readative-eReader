package com.example.usecase.books

import com.example.model.dto.BasicBookDto
import com.example.model.dto.extension.toBasicBookDto
import com.example.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetBooks  (
    private val repository: BookRepository
) {
    operator fun invoke(): Flow<List<BasicBookDto>> {
        return repository.getAllWithBasicInfo().map { it.toBasicBookDto() }
    }
}