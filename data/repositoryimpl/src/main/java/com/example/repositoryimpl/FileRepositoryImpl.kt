package com.example.repositoryimpl

import com.example.local.dao.FileDao
import com.example.model.local.entity.BookFile
import com.example.repository.FileRepository
import kotlinx.coroutines.flow.Flow

class FileRepositoryImpl(
    private val dao: FileDao
) : FileRepository {
    override fun getAll(): Flow<List<BookFile>> = dao.getFiles()

    override fun getAllByBookId(id: Long): Flow<List<BookFile>> = dao.getFilesByBookId(id)

    override suspend fun getByPath(path: String): BookFile? = dao.getFileByPath(path)

    override suspend fun getAllByPath(path: String): List<BookFile> = dao.getFilesByPath(path)

    override suspend fun getById(id: Long): BookFile? = dao.getFileById(id)

    override suspend fun insert(entity: BookFile) = dao.insertFile(entity)

    override suspend fun update(entity: BookFile) = dao.updateFile(entity)

    override suspend fun delete(entity: BookFile) = dao.deleteFile(entity)
}