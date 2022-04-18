package com.example.readative.room.junctions;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(primaryKeys = {"shelf_id", "book_id"})
public class ShelfBookCrossRef {
    @ColumnInfo(name = "shelf_id")
    public long shelfId;
    @ColumnInfo(name = "book_id")
    public long bookId;
}
