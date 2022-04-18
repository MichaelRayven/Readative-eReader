package com.example.readative.room.junctions;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(primaryKeys = {"genre_id", "book_id"})
public class GenreBookCrossRef {
    @ColumnInfo(name = "genre_id")
    public long languageId;
    @ColumnInfo(name = "book_id")
    public long bookId;
}
