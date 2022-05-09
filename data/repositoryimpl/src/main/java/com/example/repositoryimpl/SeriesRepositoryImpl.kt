package com.example.repositoryimpl

import com.example.local.dao.SeriesDao
import com.example.model.local.entity.Series
import com.example.repository.SeriesRepository
import kotlinx.coroutines.flow.Flow

class SeriesRepositoryImpl(
    private val dao: SeriesDao
) : SeriesRepository {
    override fun getAll(): Flow<List<Series>> = dao.getSeries()

    override suspend fun getById(id: Long): Series? = dao.getSeriesById(id)

    override suspend fun insert(entity: Series) = dao.insertSeries(entity)

    override suspend fun update(entity: Series) = dao.updateSeries(entity)

    override suspend fun delete(entity: Series) = dao.deleteSeries(entity)
}