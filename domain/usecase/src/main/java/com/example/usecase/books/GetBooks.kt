package com.example.usecase.books

import com.example.model.dto.BasicBookDto
import com.example.model.dto.extension.toBasicBookDto
import com.example.model.local.result_entity.BookWithBasicInfo
import com.example.repository.BookRepository
import com.example.usecase.books.util.BookOrder
import com.example.usecase.books.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetBooks  (
    private val repository: BookRepository
) {
    operator fun invoke(
        bookOrder: BookOrder = BookOrder.DateRead(OrderType.Descending)
    ): Flow<List<BookWithBasicInfo>> {
        return repository.getAllWithBasicInfo().map { books ->
            when(bookOrder.orderType) {
                is OrderType.Ascending -> {
                    when(bookOrder) {
                        is BookOrder.Title -> books.sortedBy { it.book.title.lowercase() }
                        is BookOrder.DateRead -> books.sortedBy { it.book.opened }
                        is BookOrder.DateAdded -> books.sortedBy { it.book.added }
                        is BookOrder.DatePublished -> books.sortedBy { it.book.published }
                    }
                }
                is OrderType.Descending -> {
                    when(bookOrder) {
                        is BookOrder.Title -> books.sortedByDescending { it.book.title.lowercase() }
                        is BookOrder.DateRead -> books.sortedByDescending { it.book.opened }
                        is BookOrder.DateAdded -> books.sortedByDescending { it.book.added }
                        is BookOrder.DatePublished -> books.sortedByDescending { it.book.published }
                    }
                }
            }
        }
    }
}