package com.example.repository

import kotlinx.coroutines.flow.Flow

interface Repository<T, K> {
    fun getAll(): Flow<List<T>>

    suspend fun getById(id: K): T?

    suspend fun insert(entity: T): K

    suspend fun update(entity: T)

    suspend fun delete(entity: T)
}