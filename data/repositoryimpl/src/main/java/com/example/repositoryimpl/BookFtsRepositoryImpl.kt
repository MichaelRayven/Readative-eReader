package com.example.repositoryimpl

import com.example.local.dao.BookFtsDao
import com.example.model.local.entity.Book
import com.example.model.local.result_entity.BookWithBasicInfo
import com.example.model.local.result_entity.BookWithFullInfo
import com.example.repository.BookFtsRepository
import kotlinx.coroutines.flow.Flow

class BookFtsRepositoryImpl(
    private val dao: BookFtsDao
) : BookFtsRepository {
    override fun searchForBooks(query: String): Flow<List<Book>> = dao.searchBooks(query)

    override fun searchForBooksWithBasicInfo(query: String): Flow<List<BookWithBasicInfo>> = dao.searchBooksWithBasicInfo(query)

    override fun searchForBooksWithFullInfo(query: String): Flow<List<BookWithFullInfo>> = dao.searchBooksWithFullInfo(query)
}