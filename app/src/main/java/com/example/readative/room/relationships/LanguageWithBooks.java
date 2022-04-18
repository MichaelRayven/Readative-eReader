package com.example.readative.room.relationships;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.readative.room.entity.Book;
import com.example.readative.room.entity.Language;
import com.example.readative.room.junctions.LanguageBookCrossRef;

import java.util.List;

public class LanguageWithBooks {
    @Embedded
    public Language language;
    @Relation (
            parentColumn = "language_id",
            entityColumn = "book_id",
            associateBy = @Junction(LanguageBookCrossRef.class)
    )
    public List<Book> books;
}
