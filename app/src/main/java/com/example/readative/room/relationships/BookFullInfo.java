package com.example.readative.room.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.readative.room.entity.Book;
import com.example.readative.room.entity.File;

import java.util.List;

public class BookFullInfo {
    @Embedded public Book book;
    @Relation(
            parentColumn = "book_id",
            entityColumn = "book_owner_id"
    )
    public List<FileAndArchive> files;
}
