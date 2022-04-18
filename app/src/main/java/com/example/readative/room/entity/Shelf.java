package com.example.readative.room.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Shelf {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "shelf_id")
    public long id;

    @NonNull
    @ColumnInfo(name = "shelf_name")
    public String name;
    @NonNull
    @ColumnInfo(name = "shelf_hidden")
    public boolean isHidden;
}
