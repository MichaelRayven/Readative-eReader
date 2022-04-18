package com.example.readative.room.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.readative.room.entity.Book;
import com.example.readative.room.entity.Review;

public class BookAndReview {
    @Embedded
    public Book book;
    @Relation(
            parentColumn = "book_id",
            entityColumn = "book_owner_id"
    )
    public Review review;
}
