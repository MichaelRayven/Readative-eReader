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

    @Query("SELECT * FROM authors WHERE first_name = :firstName AND middle_name = :middleName AND last_name = :lastName")
    suspend fun getAuthorByFullName(firstName: String, middleName: String?, lastName: String?): Author?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAuthor(author: Author): Long

    @Update
    suspend fun updateAuthor(author: Author)

    @Delete
    suspend fun deleteAuthor(author: Author)
}