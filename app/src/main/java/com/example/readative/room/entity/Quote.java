package com.example.readative.room.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Quote {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "quote_id")
    public long id;

    @NonNull
    @ColumnInfo(name = "book_owner_id")
    public long bookOwnerId;

    @NonNull
    @ColumnInfo(name = "quote_page")
    public int page;
    @NonNull
    @ColumnInfo(name = "quote_line")
    public int line;
    @NonNull
    @ColumnInfo(name = "quote_column")
    public int column;
    @NonNull
    @ColumnInfo(name = "quote_length")
    public int length;
    @NonNull
    @ColumnInfo(name = "quote_text")
    public String text;
    @NonNull
    @ColumnInfo(name = "quote_comment")
    public String comment;
    @NonNull
    @ColumnInfo(name = "quote_color")
    public int highlightColor;
}
