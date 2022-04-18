package com.example.readative.room.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Bookmark {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "bookmark_id")
    public long id;

    @NonNull
    @ColumnInfo(name = "book_owner_id")
    public long bookOwnerId;

    @NonNull
    @ColumnInfo(name = "bookmark_name")
    public String name;
    @NonNull
    @ColumnInfo(name = "bookmark_page")
    public int page;
}
