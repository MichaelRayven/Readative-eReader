package com.example.repository

import com.example.model.local.relation.BookShelfCrossRef

interface BookShelfCrossRefRepository: CrossRefRepository<BookShelfCrossRef, Long, Long> {
}