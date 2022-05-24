package com.example.usecase.reading

import com.example.model.local.entity.BookFile
import com.example.repository.FileRepository
import kotlinx.coroutines.flow.Flow

class GetBookFilesByBookId(
    private val repository: FileRepository
) {
    operator fun invoke(id: Long): Flow<List<BookFile>> {
        return repository.getAllByBookId(id)
    }
}