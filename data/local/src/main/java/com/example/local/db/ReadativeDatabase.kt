package com.example.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.local.converter.Converters
import com.example.local.dao.AuthorDao
import com.example.local.dao.BookDao
import com.example.local.dao.FileDao
import com.example.local.dao.SeriesDao
import com.example.model.local.entity.*
import com.example.model.local.relation.*

@Database(
    entities = [
        Book::class,
        Author::class,
        BookFile::class,
        BookFts::class,
        Bookmark::class,
        Genre::class,
        Language::class,
        Quote::class,
        Review::class,
        Series::class,
        Shelf::class,
        BookAuthorCrossRef::class,
        BookGenreCrossRef::class,
        BookLanguageCrossRef::class,
        BookSeriesCrossRef::class,
        BookShelfCrossRef::class
    ],
    exportSchema = false,
    version = 1
)
@TypeConverters(Converters::class)
abstract class ReadativeDatabase : RoomDatabase() {

    abstract val bookDao: BookDao
    abstract val authorDao: AuthorDao
    abstract val seriesDao: SeriesDao
    abstract val fileDao: FileDao

    companion object {
        const val DATABASE_NAME = "readative_db"
    }
}