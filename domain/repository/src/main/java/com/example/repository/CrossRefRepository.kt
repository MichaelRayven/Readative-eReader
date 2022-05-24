package com.example.repository

interface CrossRefRepository<T, K, S> {
    suspend fun getById(id1: K, id2: S): T?

    suspend fun insert(entity: T)

    suspend fun update(entity: T)

    suspend fun delete(entity: T)
}