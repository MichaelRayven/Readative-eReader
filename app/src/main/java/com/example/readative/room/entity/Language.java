package com.example.readative.room.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Language {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "language_id")
    public long id;

    @NonNull
    @ColumnInfo(name = "language_name")
    public String name;
    @NonNull
    @ColumnInfo(name = "language_code")
    public String code;
    @NonNull
    @ColumnInfo(name = "language_directionality")
    public String directionality;
    @NonNull
    @ColumnInfo(name = "language_default_font")
    public String defaultFont;
}
