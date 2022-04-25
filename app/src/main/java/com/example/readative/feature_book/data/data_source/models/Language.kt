package com.example.readative.feature_book.data.data_source.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.readative.feature_book.data.data_source.util.LanguageDirectionality

@Entity(
    tableName = "languages",
    indices = [
        Index(value = ["name"], unique = true),
        Index(value = ["code"], unique = true)
    ]
)
data class Language(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") override val id: Long,
    @ColumnInfo(name = "original_name") val originalName: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "code") val code: String,
    @ColumnInfo(name = "directionality") val directionality: LanguageDirectionality = LanguageDirectionality.LEFT_TO_RIGHT,
    @ColumnInfo(name = "default_font") val default_font: Int,
) : ReadativeEntity
