package com.example.readative.room.relationships;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.readative.room.entity.Book;
import com.example.readative.room.entity.Shelf;
import com.example.readative.room.junctions.ShelfBookCrossRef;

import java.util.List;

public class BookWithShelves {
    @Embedded
    public Book book;
    @Relation(
            parentColumn = "book_id",
            entityColumn = "shelf_id",
            associateBy = @Junction(ShelfBookCrossRef.class)
    )
    public List<Shelf> shelves;
}
