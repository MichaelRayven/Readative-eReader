package com.example.readative.room.junctions;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(primaryKeys = {"series_id", "book_id"})
public class SeriesBookCrossRef {
    @ColumnInfo(name = "series_id")
    public long seriesId;
    @ColumnInfo(name = "book_id")
    public long bookId;
    @ColumnInfo(name = "part")
    public int part;
}
