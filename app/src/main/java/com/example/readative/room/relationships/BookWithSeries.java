package com.example.readative.room.relationships;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.readative.room.entity.Book;
import com.example.readative.room.entity.Series;
import com.example.readative.room.junctions.SeriesBookCrossRef;

import java.util.List;

public class BookWithSeries {
    @Embedded
    public Book book;
    @Relation(
            parentColumn = "book_id",
            entityColumn = "series_id",
            associateBy = @Junction(SeriesBookCrossRef.class)
    )
    public List<Series> series;
    @Relation(
            parentColumn = "book_id",
            entityColumn = "book_id"
    )
    public List<SeriesBookCrossRef> seriesRefs;
}
