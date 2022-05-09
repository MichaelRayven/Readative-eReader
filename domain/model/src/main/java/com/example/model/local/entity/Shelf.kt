package com.example.model.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "shelves",
    indices = [
        Index(value = ["name"], unique = true)
    ]
)
data class Shelf(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") override val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "hidden_status") val isHidden: Boolean = false
) : ReadativeEntity
