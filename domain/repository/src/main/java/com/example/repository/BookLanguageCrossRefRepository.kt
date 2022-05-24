package com.example.repository

import com.example.model.local.relation.BookLanguageCrossRef

interface BookLanguageCrossRefRepository: CrossRefRepository<BookLanguageCrossRef, Long, Long> {
}