package com.example.usecase.app

import com.example.model.local.entity.BookFile
import com.example.repository.FileRepository

class AddBookFile(
    private val repository: FileRepository
) {
    suspend operator fun invoke(file: BookFile): Long {
        return repository.insert(file)
    }
}