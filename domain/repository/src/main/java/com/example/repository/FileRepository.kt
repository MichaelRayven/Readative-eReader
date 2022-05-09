package com.example.repository

import com.example.model.local.entity.BookFile
import kotlinx.coroutines.flow.Flow

interface FileRepository : Repository<BookFile, Long> {
    fun getAllByBookId(id: Long): Flow<List<BookFile>>
}