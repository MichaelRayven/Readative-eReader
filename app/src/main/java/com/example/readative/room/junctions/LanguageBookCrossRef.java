package com.example.readative.room.junctions;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(primaryKeys = {"language_id", "book_id"})
public class LanguageBookCrossRef {
    @ColumnInfo(name = "language_id")
    public long languageId;
    @ColumnInfo(name = "book_id")
    public long bookId;
}
