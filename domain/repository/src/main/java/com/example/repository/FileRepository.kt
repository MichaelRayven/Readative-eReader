package com.example.repository

import com.example.model.local.entity.BookFile
import kotlinx.coroutines.flow.Flow

interface FileRepository : Repository<BookFile, Long> {
    fun getAllByBookId(id: Long): Flow<List<BookFile>>

    suspend fun getByPath(path: String): BookFile?
    suspend fun getAllByPath(path: String): Flow<List<BookFile>>
}