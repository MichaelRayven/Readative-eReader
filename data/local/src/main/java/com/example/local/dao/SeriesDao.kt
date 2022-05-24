package com.example.local.dao

import androidx.room.*
import com.example.model.local.entity.Series
import kotlinx.coroutines.flow.Flow

@Dao
interface SeriesDao {
    @Query("SELECT * FROM series")
    fun getSeries(): Flow<List<Series>>

    @Query("SELECT * FROM series WHERE id = :id")
    suspend fun getSeriesById(id: Long): Series?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSeries(series: Series): Long

    @Update
    suspend fun updateSeries(series: Series)

    @Delete
    suspend fun deleteSeries(series: Series)
}