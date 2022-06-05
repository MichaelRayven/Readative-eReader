package com.example.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.model.local.entity.Book
import com.example.model.local.result_entity.BookWithBasicInfo
import com.example.model.local.result_entity.BookWithFullInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface BookFtsDao {
    @Transaction
    @Query("""
      SELECT *
      FROM books
      JOIN books_fts ON books.id = books_fts.rowid
      WHERE books_fts MATCH :query
    """)
    fun searchBooksWithBasicInfo(query: String): Flow<List<BookWithBasicInfo>>

    @Transaction
    @Query("""
      SELECT *
      FROM books
      JOIN books_fts ON books.id = books_fts.rowid
      WHERE books_fts MATCH :query
    """)
    fun searchBooks(query: String): Flow<List<Book>>

    @Transaction
    @Query("""
      SELECT *
      FROM books
      JOIN books_fts ON books.id = books_fts.rowid
      WHERE books_fts MATCH :query
    """)
    fun searchBooksWithFullInfo(query: String): Flow<List<BookWithFullInfo>>
}