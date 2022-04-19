package com.example.readative.viewmodel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.readative.interfaces.BookDataSource;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;

public class BookViewModel extends ViewModel {
    private final BookDataSource mDataSource;

    public BookViewModel(BookDataSource dataSource) {
        mDataSource = dataSource;
    }

    public LiveData<Bitmap> getCover() {
        return Transformations.map(mDataSource.getBook(), book -> BitmapFactory.decodeFile(book.coverUrl));
    }

    public Flowable<String> getTitle() {
        return mDataSource.getBook().map(book -> book.title);
    }

    public Flowable<String> getDescription() {
        return mDataSource.getBook().map(book -> book.description);
    }

    public Flowable<String> getAuthor() {
        return mDataSource.getAuthor().map(author -> {
            StringBuilder builder = new StringBuilder(author.firstName);
            if (author.middleName != null) builder.append(" " + author.middleName);
            if (author.lastName != null) builder.append(" " + author.lastName);
            return builder.toString();
        });
    }

    public Flowable<String> getSeries() {
        return mDataSource.getSeries().map(seriesList -> seriesList.get(0).series.name);
    }
}
