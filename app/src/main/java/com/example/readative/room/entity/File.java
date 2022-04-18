package com.example.readative.room.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class File {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "file_id")
    public long id;

    @NonNull
    @ColumnInfo(name = "book_owner_id")
    public long bookOwnerId;

    @NonNull
    @ColumnInfo(name = "file_dir")
    public String directory;
    @NonNull
    @ColumnInfo(name = "file_basename")
    public String basename;
    @NonNull
    @ColumnInfo(name = "file_format")
    public String format;
    @NonNull
    @ColumnInfo(name = "file_size")
    public String size;
}
