package com.example.local.dao

import androidx.room.*
import com.example.model.local.entity.Book
import com.example.model.local.result_entity.BookWithBasicInfo
import com.example.model.local.result_entity.BookWithFullInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Query("SELECT * FROM books")
    fun getBooks(): Flow<List<Book>>

    @Transaction
    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM books")
    fun getBasicInfoBooks(): Flow<List<BookWithBasicInfo>>

    @Transaction
    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM books")
    fun getFullInfoBooks(): Flow<List<BookWithFullInfo>>

    @Query("SELECT * FROM books WHERE id = :id")
    suspend fun getBookById(id: Long): Book?

    @Query("SELECT * FROM books WHERE checksum LIKE :checksum")
    suspend fun getBookByChecksum(checksum: String): Book?

    @Transaction
    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM books WHERE checksum LIKE :checksum")
    suspend fun getBasicInfoBookByChecksum(checksum: String): BookWithBasicInfo?

    @Transaction
    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM books WHERE id = :id")
    suspend fun getBasicInfoBookById(id: Long): BookWithBasicInfo?

    @Transaction
    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM books WHERE id = :id")
    suspend fun getFullInfoBookById(id: Long): BookWithFullInfo?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBook(book: Book): Long

    @Update
    suspend fun updateBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)
}