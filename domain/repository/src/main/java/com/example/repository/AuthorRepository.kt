package com.example.repository

import com.example.model.local.entity.Author

interface AuthorRepository : Repository<Author, Long> {
    suspend fun getByFullName(firstName: String, middleName: String?, lastName: String?): Author?
}