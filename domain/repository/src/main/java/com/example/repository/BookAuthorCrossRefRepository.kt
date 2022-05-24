package com.example.repository

import com.example.model.local.relation.BookAuthorCrossRef

interface BookAuthorCrossRefRepository : CrossRefRepository<BookAuthorCrossRef, Long, Long> {
}