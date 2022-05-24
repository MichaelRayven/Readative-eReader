package com.example.reader

import com.example.model.local.entity.Language
import java.util.*

interface ReadativeLanguage {
    val locale: Locale
    fun toLanguage(): Language
}