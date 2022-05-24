package com.example.local.dao

import androidx.room.*
import com.example.model.local.entity.Review
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewDao {
    @Query("SELECT * FROM reviews")
    fun getReviews(): Flow<List<Review>>

    @Query("SELECT * FROM reviews WHERE id = :id")
    suspend fun getReviewById(id: Long): Review?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertReview(review: Review): Long

    @Update
    suspend fun updateReview(review: Review)

    @Delete
    suspend fun deleteReview(review: Review)
}