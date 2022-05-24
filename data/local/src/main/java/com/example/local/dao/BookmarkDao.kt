package com.example.local.dao

import androidx.room.*
import com.example.model.local.entity.Bookmark
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmarks")
    fun getBookmarks(): Flow<List<Bookmark>>

    @Query("SELECT * FROM bookmarks WHERE id = :id")
    suspend fun getBookmarkById(id: Long): Bookmark?

    @Query("SELECT * FROM bookmarks WHERE book_id = :bookId")
    fun getBookmarksByBookId(bookId: Long): Flow<List<Bookmark>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBookmark(bookmark: Bookmark): Long

    @Update
    suspend fun updateBookmark(bookmark: Bookmark)

    @Delete
    suspend fun deleteBookmark(bookmark: Bookmark)
}