package com.example.local.dao

import androidx.room.*
import com.example.model.local.entity.Author
import com.example.model.local.entity.Book
import kotlinx.coroutines.flow.Flow

@Dao
interface AuthorDao {
    @Query("SELECT * FROM authors")
    fun getAuthors(): Flow<List<Author>>

    @Query("SELECT * FROM authors WHERE id = :id")
    suspend fun getAuthorById(id: Long): Author?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAuthor(author: Author)

    @Update
    suspend fun updateAuthor(author: Author)

    @Delete
    suspend fun deleteAuthor(author: Author)
}