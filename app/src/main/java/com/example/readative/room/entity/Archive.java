package com.example.readative.room.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Archive {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "archive_id")
    public long id;

    @NonNull
    @ColumnInfo(name = "file_owner_id")
    public long fileOwnerId;

    @NonNull
    @ColumnInfo(name = "archive_dir")
    public String directory;
    @NonNull
    @ColumnInfo(name = "archive_basename")
    public String basename;
    @NonNull
    @ColumnInfo(name = "archive_format")
    public String format;
}
