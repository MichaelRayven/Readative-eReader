package com.example.local.dao

import androidx.room.*
import com.example.model.local.entity.Review
import com.example.model.local.entity.Shelf
import kotlinx.coroutines.flow.Flow

@Dao
interface ShelfDao {
    @Query("SELECT * FROM shelves")
    fun getShelves(): Flow<List<Shelf>>

    @Query("SELECT * FROM shelves WHERE id = :id")
    suspend fun getShelfById(id: Long): Shelf?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertShelf(shelf: Shelf): Long

    @Update
    suspend fun updateShelf(shelf: Shelf)

    @Delete
    suspend fun deleteShelf(shelf: Shelf)
}