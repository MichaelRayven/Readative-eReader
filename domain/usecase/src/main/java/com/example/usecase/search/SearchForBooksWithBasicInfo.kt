package com.example.usecase.search

import com.example.model.local.result_entity.BookWithBasicInfo
import com.example.repository.BookFtsRepository
import kotlinx.coroutines.flow.Flow

class SearchForBooksWithBasicInfo(
    private val repository: BookFtsRepository
) {
    operator fun invoke(query: String): Flow<List<BookWithBasicInfo>> {
        return repository.searchForBooksWithBasicInfo(query)
    }
}