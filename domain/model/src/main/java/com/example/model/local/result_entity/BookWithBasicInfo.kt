package com.example.model.local.result_entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.model.local.entity.Author
import com.example.model.local.entity.Book
import com.example.model.local.entity.BookFile
import com.example.model.local.entity.Series
import com.example.model.local.relation.BookAuthorCrossRef
import com.example.model.local.relation.BookSeriesCrossRef

data class BookWithBasicInfo(
    @Embedded val book: Book,
    @Relation(
        parentColumn = "id",
        entityColumn = "book_id"
    )
    val files: List<BookFile>,
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
    val partOfSeries: List<Int?>
)
