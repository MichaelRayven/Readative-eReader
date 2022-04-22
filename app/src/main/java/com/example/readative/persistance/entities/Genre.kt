package com.example.readative.persistance.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "genres",
    indices = [
        Index(value = ["name"], unique = true)
    ]
)
data class Genre (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") override val id: Long,
    @ColumnInfo(name = "name") val name: String
) : ReadativeEntity