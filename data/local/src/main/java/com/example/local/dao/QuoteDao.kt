package com.example.local.dao

import androidx.room.*
import com.example.model.local.entity.Quote
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {
    @Query("SELECT * FROM quotes")
    fun getQuotes(): Flow<List<Quote>>

    @Query("SELECT * FROM quotes WHERE id = :id")
    suspend fun getQuoteById(id: Long): Quote?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuote(quote: Quote): Long

    @Update
    suspend fun updateQuote(quote: Quote)

    @Delete
    suspend fun deleteQuote(quote: Quote)
}