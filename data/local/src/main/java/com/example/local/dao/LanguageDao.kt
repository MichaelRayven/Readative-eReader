package com.example.local.dao

import androidx.room.*
import com.example.model.local.entity.Language
import kotlinx.coroutines.flow.Flow

@Dao
interface LanguageDao {
    @Query("SELECT * FROM languages")
    fun getLanguages(): Flow<List<Language>>

    @Query("SELECT * FROM languages WHERE id = :id")
    suspend fun getLanguageById(id: Long): Language?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLanguage(language: Language): Long

    @Update
    suspend fun updateLanguage(language: Language)

    @Delete
    suspend fun deleteLanguage(language: Language)
}