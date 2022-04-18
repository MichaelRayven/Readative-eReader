package com.example.readative.room.entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Review {
    public static final int RATING_MIN = 1;
    public static final int RATING_MAX = 5;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "review_id")
    public long id;

    @NonNull
    @ColumnInfo(name = "book_owner_id")
    public long bookOwnerId;

    @NonNull
    @ColumnInfo(name = "review_rating")
    public int rating;
    @ColumnInfo(name = "review_content")
    public String content;

    public Review() {
        this.id = id;
        this.bookOwnerId = bookOwnerId;
        this.content = content;
        if (RATING_MIN <= rating && rating <= RATING_MAX) {
            this.rating = rating;
        } else if (RATING_MIN > rating) {
            this.rating = RATING_MIN;
        } else {
            this.rating = RATING_MAX;
        }
    }
}
