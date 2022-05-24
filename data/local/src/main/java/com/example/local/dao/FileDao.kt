package com.example.local.dao

import androidx.room.*
import com.example.model.local.entity.BookFile
import kotlinx.coroutines.flow.Flow

@Dao
interface FileDao {
    @Query("SELECT * FROM files")
    fun getFiles(): Flow<List<BookFile>>

    @Query("SELECT * FROM files WHERE book_id = :bookId")
    fun getFilesByBookId(bookId: Long): Flow<List<BookFile>>

    @Query("SELECT * FROM files WHERE id = :id")
    suspend fun getFileById(id: Long): BookFile?

    @Query("SELECT * FROM files WHERE path LIKE :path")
    fun getFilesByPath(path: String): Flow<List<BookFile>>

    @Query("SELECT * FROM files WHERE path LIKE :path")
    suspend fun getFileByPath(path: String): BookFile?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFile(file: BookFile): Long

    @Update
    suspend fun updateFile(file: BookFile)

    @Delete
    suspend fun deleteFile(file: BookFile)
}