package com.example.repositoryimpl

import com.example.local.dao.CrossRefsDao
import com.example.model.local.relation.BookAuthorCrossRef
import com.example.repository.BookAuthorCrossRefRepository

class BookAuthorCrossRefRepositoryImpl(
    private val dao: CrossRefsDao
): BookAuthorCrossRefRepository {
    override suspend fun getById(id1: Long, id2: Long): BookAuthorCrossRef? = dao.getBookAuthorCrossRef(id1, id2)

    override suspend fun insert(entity: BookAuthorCrossRef) = dao.insertBookAuthorCrossRef(entity)

    override suspend fun update(entity: BookAuthorCrossRef) = dao.updateBookAuthorCrossRef(entity)

    override suspend fun delete(entity: BookAuthorCrossRef) = dao.deleteBookAuthorCrossRef(entity)
}