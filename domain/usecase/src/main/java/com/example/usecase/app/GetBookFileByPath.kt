package com.example.usecase.app

import com.example.model.local.entity.BookFile
import com.example.repository.FileRepository
import kotlinx.coroutines.flow.Flow

class GetBookFileByPath(
    private val repository: FileRepository
) {
    suspend operator fun invoke(path: String): Flow<List<BookFile>> {
        return repository.getAllByPath(path)
    }
}