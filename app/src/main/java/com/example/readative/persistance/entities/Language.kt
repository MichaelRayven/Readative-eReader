package com.example.readative.persistance.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "languages",
    indices = [
        Index(value = ["name"], unique = true),
        Index(value = ["code"], unique = true)
    ]
)
data class Language(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") override val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "code") val code: String,
    @ColumnInfo(name = "directionality") val directionality: LanguageDirectionality = LanguageDirectionality.LEFT_TO_RIGHT,
    @ColumnInfo(name = "default_font") val default_font: Int,
) : ReadativeEntity
