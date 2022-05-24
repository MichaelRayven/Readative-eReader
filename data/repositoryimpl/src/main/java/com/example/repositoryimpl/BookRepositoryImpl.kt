package com.example.repositoryimpl

import com.example.local.dao.BookDao
import com.example.model.local.entity.Book
import com.example.model.local.result_entity.BookWithBasicInfo
import com.example.model.local.result_entity.BookWithFullInfo
import com.example.repository.BookRepository
import kotlinx.coroutines.flow.Flow


class BookRepositoryImpl(
    private val dao: BookDao
) : BookRepository {
    override fun getAllWithBasicInfo(): Flow<List<BookWithBasicInfo>> = dao.getBasicInfoBooks()

    override fun getAllWithFullInfo(): Flow<List<BookWithFullInfo>> = dao.getFullInfoBooks()

    override suspend fun getByIdWithFullInfo(id: Long): BookWithFullInfo? = dao.getFullInfoBookById(id)

    override suspend fun getByIdWithBasicInfo(id: Long): BookWithBasicInfo? = dao.getBasicInfoBookById(id)

    override suspend fun getByChecksumWithBasicInfo(checksum: String): BookWithBasicInfo? = dao.getBasicInfoBookByChecksum(checksum)

    override suspend fun getByChecksum(checksum: String): Book? = dao.getBookByChecksum(checksum)

    override fun getAll(): Flow<List<Book>> = dao.getBooks()

    override suspend fun getById(id: Long): Book? = dao.getBookById(id)

    override suspend fun insert(entity: Book) = dao.insertBook(entity)

    override suspend fun update(entity: Book) = dao.updateBook(entity)

    override suspend fun delete(entity: Book) = dao.deleteBook(entity)
}