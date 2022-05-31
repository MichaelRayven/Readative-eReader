package com.example.usecase.reading

import com.example.usecase.common.GetBook
import com.example.usecase.common.UpdateBook

data class ReadingUseCases (
    val getBookFiles: GetBookFilesByBookId,
    val getBookContents: GetBookContents,
    val getBook: GetBook,
    val updateBook: UpdateBook
)