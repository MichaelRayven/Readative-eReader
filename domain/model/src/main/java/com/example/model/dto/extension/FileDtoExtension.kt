package com.example.model.dto.extension

import com.example.model.dto.BasicBookFileDto
import com.example.model.dto.BookFileDto
import com.example.model.local.entity.BookFile

fun List<BookFile>.toBookFileDto() = map { it.toBookFileDto() }

fun List<BookFile>.toBasicBookFileDto() = map { it.toBasicBookFileDto() }

fun BookFile.toBookFileDto() = BookFileDto(
    id = id,
    path = path,
    fileSize = size.toString(),
    bookFormat = format
)

fun BookFile.toBasicBookFileDto() = BasicBookFileDto(
    fileSize = size.toString(),
    bookFormat = format
)