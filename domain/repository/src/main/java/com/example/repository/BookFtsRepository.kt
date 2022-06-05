package com.example.repository

import com.example.model.local.entity.Book
import com.example.model.local.result_entity.BookWithBasicInfo
import com.example.model.local.result_entity.BookWithFullInfo
import kotlinx.coroutines.flow.Flow

interface BookFtsRepository {
    fun searchForBooks(query: String): Flow<List<Book>>
    fun searchForBooksWithBasicInfo(query: String): Flow<List<BookWithBasicInfo>>
    fun searchForBooksWithFullInfo(query: String): Flow<List<BookWithFullInfo>>
}