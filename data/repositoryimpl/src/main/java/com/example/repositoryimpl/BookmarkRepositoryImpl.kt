package com.example.repositoryimpl

import com.example.local.dao.BookmarkDao
import com.example.model.local.entity.Bookmark
import com.example.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow

class BookmarkRepositoryImpl(
    private val dao: BookmarkDao
): BookmarkRepository {
    override suspend fun getAllByBookId(id: Long): Flow<List<Bookmark>> = dao.getBookmarksByBookId(id)

    override fun getAll(): Flow<List<Bookmark>> = dao.getBookmarks()

    override suspend fun getById(id: Long): Bookmark? = dao.getBookmarkById(id)

    override suspend fun insert(entity: Bookmark): Long = dao.insertBookmark(entity)

    override suspend fun update(entity: Bookmark) = dao.updateBookmark(entity)

    override suspend fun delete(entity: Bookmark) = dao.deleteBookmark(entity)
}