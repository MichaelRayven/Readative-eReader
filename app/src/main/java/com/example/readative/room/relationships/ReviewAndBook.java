package com.example.readative.room.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.readative.room.entity.Book;
import com.example.readative.room.entity.Review;

public class ReviewAndBook {
    @Embedded
    public Review review;
    @Relation(
            parentColumn = "book_owner_id",
            entityColumn = "book_id"
    )
    public Book book;
}
