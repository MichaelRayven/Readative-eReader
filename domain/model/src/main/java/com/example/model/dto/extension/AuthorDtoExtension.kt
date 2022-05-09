package com.example.model.dto.extension

import com.example.framework.extension.whenNonNull
import com.example.model.dto.AuthorDto
import com.example.model.dto.BasicAuthorDto
import com.example.model.local.entity.Author


fun List<Author>.toAuthorDto() = map { it.toAuthorDto() }

fun List<Author>.toBasicAuthorDto() = map { it.toBasicAuthorDto() }

fun Author.toAuthorDto() = AuthorDto(
    id = id,
    firstName = firstName,
    middleName = middleName,
    lastName = lastName
)

fun Author.toBasicAuthorDto() = BasicAuthorDto(
    shortenedFullName = lastName.whenNonNull(
        firstName + middleName.whenNonNull { " $it" }
    ) { lastName_ ->
        firstName[0] + ". " + middleName.whenNonNull { it[0] + ". " } + lastName_
    }.toString()
)