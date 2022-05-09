package com.example.repositoryimpl

import com.example.local.dao.AuthorDao
import com.example.model.local.entity.Author
import com.example.repository.AuthorRepository
import kotlinx.coroutines.flow.Flow

class AuthorRepositoryImpl(
    private val dao: AuthorDao
) : AuthorRepository {
    override fun getAll(): Flow<List<Author>> = dao.getAuthors()

    override suspend fun getById(id: Long): Author? = dao.getAuthorById(id)

    override suspend fun insert(entity: Author) = dao.insertAuthor(entity)

    override suspend fun update(entity: Author) = dao.updateAuthor(entity)

    override suspend fun delete(entity: Author) = dao.deleteAuthor(entity)
}