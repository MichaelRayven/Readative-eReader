package com.example.readative.room.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Genre {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "genre_id")
    public long id;

    @NonNull
    @ColumnInfo(name = "genre_name")
    public String name;
}
