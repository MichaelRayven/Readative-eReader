package com.example.model.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "languages",
    indices = [
        Index(value = ["locale"], unique = true)
    ]
)
data class Language(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") override val id: Long,
    @ColumnInfo(name = "locale") val locale: Locale,
    @ColumnInfo(name = "directionality") val directionality: com.example.model.local.util.LanguageDirectionality = com.example.model.local.util.LanguageDirectionality.LEFT_TO_RIGHT,
    @ColumnInfo(name = "default_font") val default_font: Int,
    @ColumnInfo(name = "primary_status") val isPrimary: Boolean = false
) : ReadativeEntity
