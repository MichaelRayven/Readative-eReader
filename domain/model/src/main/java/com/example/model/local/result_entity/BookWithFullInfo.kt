package com.example.model.local.result_entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.model.local.entity.*
import com.example.model.local.relation.*

data class BookWithFullInfo (
    @Embedded
    val book: Book,
    @Relation(
        parentColumn = "id",
        entityColumn = "book_id"
    )
    val files: List<BookFile>,
    @Relation(
        parentColumn = "id",
        entityColumn = "book_id"
    )
    val bookmarks: List<Bookmark>,
    @Relation(
        parentColumn = "id",
        entityColumn = "book_id"
    )
    val quotes: List<Quote>,
    @Relation(
        parentColumn = "id",
        entityColumn = "book_id"
    )
    val review: Review,
    @Relation(
        parentColumn = "id",
        entity = Author::class,
        entityColumn = "id",
        associateBy = Junction(
            value = BookAuthorCrossRef::class,
            parentColumn = "book_id",
            entityColumn = "author_id"
        )
    )
    val authors: List<Author>,
    @Relation(
        parentColumn = "id",
        entity = Language::class,
        entityColumn = "id",
        associateBy = Junction(
            value = BookLanguageCrossRef::class,
            parentColumn = "book_id",
            entityColumn = "language_id"
        )
    )
    val languages: List<Language>,
    @Relation(
        parentColumn = "id",
        entity = Genre::class,
        entityColumn = "id",
        associateBy = Junction(
            value = BookGenreCrossRef::class,
            parentColumn = "book_id",
            entityColumn = "genre_id"
        )
    )
    val genres: List<Genre>,
    @Relation(
        parentColumn = "id",
        entity = Shelf::class,
        entityColumn = "id",
        associateBy = Junction(
            value = BookShelfCrossRef::class,
            parentColumn = "book_id",
            entityColumn = "shelf_id"
        )
    )
    val shelves: List<Shelf>,
    @Relation(
        parentColumn = "id",
        entity = Series::class,
        entityColumn = "id",
        associateBy = Junction(
            value = BookSeriesCrossRef::class,
            parentColumn = "book_id",
            entityColumn = "series_id"
        )
    )
    val series: List<Series>,
    @Relation(
        parentColumn = "id",
        entity = BookSeriesCrossRef::class,
        entityColumn = "book_id",
        projection = ["part"]
    )
    val partOfSeries: List<Int>
)