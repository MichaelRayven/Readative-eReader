package com.example.repository

import com.example.model.local.entity.Book
import com.example.model.local.result_entity.BookWithBasicInfo
import com.example.model.local.result_entity.BookWithFullInfo
import kotlinx.coroutines.flow.Flow

interface BookRepository : Repository<Book, Long> {
    fun getAllWithBasicInfo(): Flow<List<BookWithBasicInfo>>

    fun getAllWithFullInfo(): Flow<List<BookWithFullInfo>>

    suspend fun getByIdWithFullInfo(id: Long): BookWithFullInfo?

    suspend fun getByIdWithBasicInfo(id: Long): BookWithBasicInfo?

    suspend fun getByChecksumWithBasicInfo(checksum: String): BookWithBasicInfo?

    suspend fun getByChecksum(checksum: String): Book?
}