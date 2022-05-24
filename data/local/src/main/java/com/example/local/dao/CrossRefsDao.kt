package com.example.local.dao

import androidx.room.*
import com.example.model.local.relation.*

@Dao
interface CrossRefsDao {
    @Query("SELECT * FROM BookAuthorCrossRef WHERE book_id = :bookId AND author_id = :authorId")
    suspend fun getBookAuthorCrossRef(bookId: Long, authorId: Long): BookAuthorCrossRef?

    @Update
    suspend fun updateBookAuthorCrossRef(crossRef: BookAuthorCrossRef)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBookAuthorCrossRef(crossRef: BookAuthorCrossRef)

    @Delete
    suspend fun deleteBookAuthorCrossRef(crossRef: BookAuthorCrossRef)



    @Query("SELECT * FROM BookSubjectCrossRef WHERE book_id = :bookId AND subject_id = :subjectId")
    suspend fun getBookSubjectCrossRef(bookId: Long, subjectId: Long): BookSubjectCrossRef?

    @Update
    suspend fun updateBookSubjectCrossRef(crossRef: BookSubjectCrossRef)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBookSubjectCrossRef(crossRef: BookSubjectCrossRef)

    @Delete
    suspend fun deleteBookSubjectCrossRef(crossRef: BookSubjectCrossRef)



    @Query("SELECT * FROM BookLanguageCrossRef WHERE book_id = :bookId AND language_id = :languageId")
    suspend fun getBookLanguageCrossRef(bookId: Long, languageId: Long): BookLanguageCrossRef?

    @Update
    suspend fun updateBookLanguageCrossRef(crossRef: BookLanguageCrossRef)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBookLanguageCrossRef(crossRef: BookLanguageCrossRef)

    @Delete
    suspend fun deleteBookLanguageCrossRef(crossRef: BookLanguageCrossRef)



    @Query("SELECT * FROM BookSeriesCrossRef WHERE book_id = :bookId AND series_id = :seriesId")
    suspend fun getBookSeriesCrossRef(bookId: Long, seriesId: Long): BookSeriesCrossRef?

    @Update
    suspend fun updateBookSeriesCrossRef(crossRef: BookSeriesCrossRef)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBookSeriesCrossRef(crossRef: BookSeriesCrossRef)

    @Delete
    suspend fun deleteBookSeriesCrossRef(crossRef: BookSeriesCrossRef)



    @Query("SELECT * FROM BookShelfCrossRef WHERE book_id = :bookId AND shelf_id = :shelfId")
    suspend fun getBookShelfCrossRef(bookId: Long, shelfId: Long): BookShelfCrossRef?

    @Update
    suspend fun updateBookShelfCrossRef(crossRef: BookShelfCrossRef)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBookShelfCrossRef(crossRef: BookShelfCrossRef)

    @Delete
    suspend fun deleteBookShelfCrossRef(crossRef: BookShelfCrossRef)
}