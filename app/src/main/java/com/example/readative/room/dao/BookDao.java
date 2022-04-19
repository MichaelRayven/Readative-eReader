package com.example.readative.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.readative.room.entity.Book;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface BookDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public Completable insertBooks(Book... books);

    @Update
    public Completable updateBooks(Book... books);

    @Delete
    public Completable deleteBooks(Book... books);

    @Query("SELECT * FROM book")
    public Flowable<List<Book>> loadAllBooks();

    @Query("SELECT * FROM book WHERE book_id = :id")
    public Flowable<Book> loadBookById(long id);

    @Query("SELECT book_id FROM book WHERE book_uuid = :uuid")
    public Single<Integer> loadBookIdByUUID(String uuid);

    @Query("SELECT * FROM book WHERE author_owner_id = :authorId")
    public Flowable<List<Book>> loadBooksByAuthor(long authorId);
}
