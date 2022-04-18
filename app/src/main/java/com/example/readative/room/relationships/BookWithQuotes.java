package com.example.readative.room.relationships;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.readative.room.entity.Book;
import com.example.readative.room.entity.Quote;

import java.util.List;

public class BookWithQuotes {
    @Embedded
    public Book book;
    @Relation(
            parentColumn = "book_id",
            entityColumn = "book_owner_id"
    )
    public List<Quote> quotes;
}
