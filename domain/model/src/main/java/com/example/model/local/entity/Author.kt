package com.example.model.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "authors",
    indices = [
        Index(value = ["first_name", "middle_name", "last_name"], unique = true)
    ]
)
data class Author (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") override val id: Long,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "middle_name") val middleName: String?,
    @ColumnInfo(name = "last_name") val lastName: String?
) : ReadativeEntity