package com.example.readative.room.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Author {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "author_id")
    public long id;

    @NonNull
    @ColumnInfo(name = "author_first_name")
    public String firstName;
    @ColumnInfo(name = "author_middle_name")
    public String middleName;
    @ColumnInfo(name = "author_last_name")
    public String lastName;
}
