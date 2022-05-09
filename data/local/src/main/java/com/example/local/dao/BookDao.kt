package com.example.local.dao

import androidx.room.*
import com.example.model.local.entity.Book
import com.example.model.local.result_entity.BookWithBasicInfo
import com.example.model.local.result_entity.BookWithFullInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Transaction
    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM books")
    fun getBasicInfoBooks(): Flow<List<BookWithBasicInfo>>

    @Transaction
    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM books WHERE id = :id")
    suspend fun getFullInfoBookById(id: Long): BookWithFullInfo?

    @Query("SELECT * FROM books")
    fun getBooks(): Flow<List<Book>>

    @Query("SELECT * FROM books WHERE id = :id")
    suspend fun getBookById(id: Long): Book?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBook(book: Book)

    @Update
    suspend fun updateBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)
}