package com.example.repository

import com.example.model.local.relation.BookSubjectCrossRef

interface BookSubjectCrossRefRepository: CrossRefRepository<BookSubjectCrossRef, Long, Long> {
}