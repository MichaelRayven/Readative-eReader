package com.example.readative.di

import android.app.Application
import androidx.room.Room
import com.example.local.db.ReadativeDatabase
import com.example.reader.ReadativeBookReader
import com.example.repository.*
import com.example.repositoryimpl.*
import com.example.usecase.app.*
import com.example.usecase.books.BookUseCases
import com.example.usecase.books.GetBook
import com.example.usecase.books.GetBooks
import com.example.usecase.files.GetFiles
import com.example.usecase.files.StorageUseCases
import com.example.usecase.reading.GetBookFilesByBookId
import com.example.usecase.reading.GetBookPages
import com.example.usecase.reading.ReadingUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): ReadativeDatabase {
        return Room.databaseBuilder(
            app,
            ReadativeDatabase::class.java,
            ReadativeDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideBookRepository(db: ReadativeDatabase): BookRepository {
        return BookRepositoryImpl(db.bookDao)
    }

    @Provides
    @Singleton
    fun provideAuthorRepository(db: ReadativeDatabase): AuthorRepository {
        return AuthorRepositoryImpl(db.authorDao)
    }

    @Provides
    @Singleton
    fun provideSeriesRepository(db: ReadativeDatabase): SeriesRepository {
        return SeriesRepositoryImpl(db.seriesDao)
    }

    @Provides
    @Singleton
    fun provideFileRepository(db: ReadativeDatabase): FileRepository {
        return FileRepositoryImpl(db.fileDao)
    }

    @Provides
    @Singleton
    fun provideBookAuthorCrossRefRepository(db: ReadativeDatabase): BookAuthorCrossRefRepository {
        return BookAuthorCrossRefRepositoryImpl(db.crossRefsDao)
    }

    @Provides
    @Singleton
    fun provideBookUseCases(repository: BookRepository): BookUseCases {
        return BookUseCases(
            getBooks = GetBooks(repository),
            getBook = GetBook(repository)
        )
    }

    @Provides
    @Singleton
    fun provideStorageUseCases(
        bookRepository: BookRepository,
        fileRepository: FileRepository
    ): StorageUseCases {
        return StorageUseCases(
            getFiles = GetFiles(
                bookRepository,
                fileRepository
            )
        )
    }

    @Provides
    @Singleton
    fun provideAppUseCases(
        bookRepository: BookRepository,
        fileRepository: FileRepository,
        authorRepository: AuthorRepository,
        bookAuthorCrossRefRepository: BookAuthorCrossRefRepository
    ): AppUseCases {
        return AppUseCases(
            addBook = AddBook(bookRepository),
            addAuthor = AddAuthor(authorRepository, bookAuthorCrossRefRepository),
            addBookFile = AddBookFile(fileRepository),
            getBookByChecksum = GetBookByChecksum(bookRepository),
            getBookFileByPath = GetBookFileByPath(fileRepository)
        )
    }

//    @Provides
//    fun providePdfiumCore(app: Application): PdfiumCore {
//        return PdfiumCore(app.applicationContext)
//    }

    @Singleton
    @Provides
    fun provideReadativeBookReader(app: Application): ReadativeBookReader {
        return ReadativeBookReader(app.applicationContext)
    }

    @Provides
    @Singleton
    fun provideReadingUseCases(
        fileRepository: FileRepository,
        bookReader: ReadativeBookReader
    ): ReadingUseCases {
        return ReadingUseCases(
            getBookFiles = GetBookFilesByBookId(fileRepository),
            getBookPages = GetBookPages(bookReader)
        )
    }
}