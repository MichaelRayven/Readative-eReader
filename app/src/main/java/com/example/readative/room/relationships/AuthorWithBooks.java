package com.example.readative.room.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.readative.room.entity.Author;
import com.example.readative.room.entity.Book;

import java.util.List;

public class AuthorWithBooks {
    @Embedded
    public Author author;
    @Relation (
            parentColumn = "author_id",
            entityColumn = "author_owner_id"
    )
    public List<Book> books;
}
