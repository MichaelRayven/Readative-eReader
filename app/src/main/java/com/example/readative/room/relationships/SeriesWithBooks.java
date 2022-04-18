package com.example.readative.room.relationships;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.readative.room.entity.Book;
import com.example.readative.room.entity.Series;
import com.example.readative.room.junctions.SeriesBookCrossRef;

import java.util.List;

public class SeriesWithBooks {
    @Embedded
    public Series series;
    @Relation(
            parentColumn = "series_id",
            entityColumn = "book_id",
            associateBy = @Junction(SeriesBookCrossRef.class)
    )
    public List<Book> books;
    @Relation(
            parentColumn = "series_id",
            entityColumn = "series_id"
    )
    public List<SeriesBookCrossRef> booksRefs;
}
