package com.example.readative.room.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.readative.room.dao.BookDao;
import com.example.readative.room.entity.Archive;
import com.example.readative.room.entity.Author;
import com.example.readative.room.entity.Book;
import com.example.readative.room.entity.Bookmark;
import com.example.readative.room.entity.File;
import com.example.readative.room.entity.Genre;
import com.example.readative.room.entity.Language;
import com.example.readative.room.entity.Quote;
import com.example.readative.room.entity.Review;
import com.example.readative.room.entity.Series;
import com.example.readative.room.entity.Shelf;
import com.example.readative.util.Converters;

@Database(entities = {Archive.class, File.class, Author.class,
        Series.class, Genre.class, Language.class,
        Quote.class, Review.class, Bookmark.class,
        Book.class, Shelf.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract BookDao bookDao();
}
