package com.example.repository

import com.example.model.local.entity.Bookmark
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository: Repository<Bookmark, Long> {
    suspend fun getAllByBookId(id: Long): Flow<List<Bookmark>>
}