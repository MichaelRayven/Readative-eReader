package com.example.readative.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Book {
    @PrimaryKey(autoGenerate = true)
    public int uuid;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "author")
    public String author;

    @ColumnInfo(name = "series")
    public String series;

    @ColumnInfo(name = "annotation")
    public String annotation;

    @ColumnInfo(name = "progress")
    public float progress;
}
