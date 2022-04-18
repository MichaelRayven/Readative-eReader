package com.example.readative.room.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"series_name"}, unique = true)})
public class Series {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "series_id")
    public long id;

    @NonNull
    @ColumnInfo(name = "series_name")
    public String name;
}
