package com.example.reader.epub

import com.example.model.local.entity.Language
import com.example.model.local.util.LanguageDirectionality
import com.example.reader.ReadativeLanguage
import java.util.*

class EpubLanguage(
    private val language: String
): ReadativeLanguage {
    override val locale: Locale
        get() = Locale(language)

    override fun toLanguage(): Language {
        val directionality = Character.getDirectionality(locale.displayName[0])
        val langDirectionality = if (directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT ||
            directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC ||
            directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_EMBEDDING ||
            directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_OVERRIDE)
                LanguageDirectionality.RIGHT_TO_LEFT else LanguageDirectionality.LEFT_TO_RIGHT

        return Language(
            0,
            locale,
            langDirectionality
        )
    }
}