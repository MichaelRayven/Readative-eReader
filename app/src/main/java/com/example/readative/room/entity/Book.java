package com.example.readative.room.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.readative.util.Converters;

import java.util.Date;

@Entity(indices = {@Index(value = {"book_uuid"}, unique = true)})
public class Book {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "book_id")
    public long id;
    @ColumnInfo(name = "book_uuid")
    public String uuid;

    @ColumnInfo(name = "author_owner_id")
    public int authorOwnerId;

    @NonNull
    @ColumnInfo(name = "book_title")
    public String title;
    @ColumnInfo(name = "book_description")
    public String description;
    @NonNull
    @ColumnInfo(name = "book_cover")
    public String coverUrl;
    @NonNull
    @ColumnInfo(name = "book_progress")
    public int progress;
    @NonNull
    @ColumnInfo(name = "book_status")
    public Converters.BookStatus status;
    @NonNull
    @ColumnInfo(name = "book_date_modified")
    public Date modified;
    @NonNull
    @ColumnInfo(name = "book_date_added")
    public Date added;
    @ColumnInfo(name = "book_date_opened")
    public Date lastOpened;
}
