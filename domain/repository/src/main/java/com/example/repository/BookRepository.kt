package com.example.repository

import com.example.model.local.entity.Book
import com.example.model.local.result_entity.BookWithBasicInfo
import com.example.model.local.result_entity.BookWithFullInfo
import kotlinx.coroutines.flow.Flow

interface BookRepository : Repository<Book, Long> {
    fun getAllWithBasicInfo(): Flow<List<BookWithBasicInfo>>

    suspend fun getByIdWithFullInfo(id: Long): BookWithFullInfo?
}