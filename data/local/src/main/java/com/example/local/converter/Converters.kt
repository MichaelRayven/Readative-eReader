package com.example.local.converter

import android.graphics.Color
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.model.local.util.BookFormat
import com.example.model.local.util.FileSize
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId
import java.util.*

class Converters {
    @TypeConverter
    fun StringToLocale(string: String?): Locale? {
        val locale = Locale.Builder()
            .setLanguage(string?.substring(0, 2))
            .setRegion(string?.substring(3, 5))
            .build()

        return if(isLocaleValid(locale)) locale else null
    }

    @TypeConverter
    fun LocaleToString(locale: Locale?): String? {
        if(isLocaleValid(locale)) {
            return locale.toString()
        }

        return null
    }

    @TypeConverter
    fun OffsetDateTimeToLong(dateTime: OffsetDateTime?): Long? {
        return dateTime?.toEpochSecond()
    }

    @TypeConverter
    fun LongToOffsetDateTime(seconds: Long?): OffsetDateTime? {
        return seconds?.let { OffsetDateTime.ofInstant(
            Instant.ofEpochSecond(it),
            ZoneId.systemDefault()
        ) }
    }

    @TypeConverter
    fun BookFormatToString(bookFormat: BookFormat) = bookFormat.toString()

    @TypeConverter
    fun StringToBookFormat(string: String): BookFormat = BookFormat.valueOf(string)

    @TypeConverter
    fun LongToFileSize(byteSize: Long?): FileSize? {
        return byteSize?.let { FileSize(it) }
    }

    @TypeConverter
    fun FileSizeToLong(fileSize: FileSize?): Long? {
        return fileSize?.getByteSize()
    }

    @TypeConverter
    fun IntToColor(int: Int?): Color? {
        return int?.let { Color.valueOf(it) }
    }

    @TypeConverter
    fun ColorToInt(color: Color?): Int? {
        return color?.toArgb()
    }

    private fun isLocaleValid(locale: Locale?): Boolean {
        return locale?.isO3Language != "" && locale?.isO3Country != ""
    }
}