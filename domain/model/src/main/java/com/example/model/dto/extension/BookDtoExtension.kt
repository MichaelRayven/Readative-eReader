package com.example.model.dto.extension

import com.example.model.dto.BasicBookDto
import com.example.model.local.result_entity.BookWithBasicInfo

fun List<BookWithBasicInfo>.toBasicBookDto() = map { it.toBasicBookDto() }

fun BookWithBasicInfo.toBasicBookDto() = BasicBookDto(
    id = book.id,
    title = book.title,
    bookCover = book.coverPath,
    readingProgress = book.progress,
    file = if (files.isNotEmpty()) files[0].toBasicBookFileDto() else null,
    series = series.toSeriesDto(),
    authors = authors.toBasicAuthorDto()
)