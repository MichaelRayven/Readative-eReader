package com.example.readative.interfaces;

import androidx.lifecycle.LiveData;

import com.example.readative.room.entity.Author;
import com.example.readative.room.entity.Book;
import com.example.readative.room.entity.Bookmark;
import com.example.readative.room.entity.Genre;
import com.example.readative.room.entity.Language;
import com.example.readative.room.entity.PartOfSeries;
import com.example.readative.room.entity.Quote;
import com.example.readative.room.entity.Review;
import com.example.readative.room.entity.Series;
import com.example.readative.room.entity.Shelf;
import com.example.readative.room.relationships.FileAndArchive;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface BookDataSource {
    LiveData<Book> getBook();
    Flowable<Author> getAuthor();
    Flowable<Review> getReview();

    Flowable<List<FileAndArchive>> getFiles();
    Flowable<List<Language>> getLanguages();
    Flowable<List<Quote>> getQuotes();
    Flowable<List<Genre>> getGenres();
    Flowable<List<Bookmark>> getBookmarks();
    Flowable<List<PartOfSeries>> getSeries();
    Flowable<List<Shelf>> getShelves();
}
