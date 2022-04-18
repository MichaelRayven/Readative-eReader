package com.example.readative.room.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.readative.room.entity.Author;
import com.example.readative.room.entity.Book;

public class BookAndAuthor {
    @Embedded
    public Book book;
    @Relation(
            parentColumn = "author_owner_id",
            entityColumn = "author_id"
    )
    public Author author;
}
