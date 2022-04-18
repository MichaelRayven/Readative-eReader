package com.example.readative.room.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.readative.room.entity.Archive;
import com.example.readative.room.entity.File;

public class FileAndArchive {
    @Embedded
    public File file;
    @Relation(
            parentColumn = "file_id",
            entityColumn = "file_owner_id"
    )
    public Archive archive;
}
