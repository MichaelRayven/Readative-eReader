package com.example.readative.di

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.storage.StorageManager
import android.os.storage.StorageVolume
import android.speech.tts.TextToSpeech
import androidx.room.Room
import com.example.local.db.ReadativeDatabase
import com.example.reader.ReadativeBookReader
import com.example.reading.BookReadingMiddleware
import com.example.reading.CleanupMiddleware
import com.example.reading.TTSMiddleware
import com.example.reading.service.AudioBookService
import com.example.reading.util.TTSInitListener
import com.example.repository.*
import com.example.repositoryimpl.*
import com.example.usecase.app.*
import com.example.usecase.books.BookUseCases
import com.example.usecase.common.GetBook
import com.example.usecase.books.GetBooks
import com.example.usecase.common.UpdateBook
import com.example.usecase.files.GetFiles
import com.example.usecase.files.StorageUseCases
import com.example.usecase.reading.GetBookContents
import com.example.usecase.reading.GetBookFilesByBookId
import com.example.usecase.reading.GetBookPages
import com.example.usecase.reading.ReadingUseCases
import com.example.usecase.search.SearchForBooksWithBasicInfo
import com.example.usecase.search.SearchUseCases
import com.shockwave.pdfium.PdfiumCore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Named
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

    @Provides
    @Singleton
    fun provideBookFtsRepository(db: ReadativeDatabase): BookFtsRepository {
        return BookFtsRepositoryImpl(db.bookFtsDao)
    }

    @Provides
    @Singleton
    fun provideSearchUseCases(
        bookFtsRepository: BookFtsRepository
    ): SearchUseCases {
        return SearchUseCases(
            SearchForBooksWithBasicInfo(bookFtsRepository)
        )
    }

//    @Provides
//    fun providePdfiumCore(app: Application): PdfiumCore {
//        return PdfiumCore(app.applicationContext)
//    }

    @Singleton
    @Provides
    fun provideReadativeBookReader(app: Application, pdfiumCore: PdfiumCore): ReadativeBookReader {
        return ReadativeBookReader(app.applicationContext, pdfiumCore)
    }

    @Provides
    @Singleton
    fun provideReadingUseCases(
        fileRepository: FileRepository,
        bookRepository: BookRepository,
        bookReader: ReadativeBookReader
    ): ReadingUseCases {
        return ReadingUseCases(
            getBookFiles = GetBookFilesByBookId(fileRepository),
            getBookContents = GetBookContents(bookReader),
            getBook = GetBook(bookRepository),
            updateBook = UpdateBook(bookRepository)
        )
    }

    @Singleton
    @Provides
    fun provideTTS(app: Application): TextToSpeech {
        return TextToSpeech(app.applicationContext, TTSInitListener());
    }

    @Singleton
    @Provides
    fun provideServiceIntent(app: Application): Intent {
        return Intent(app.applicationContext, AudioBookService::class.java)
    }

    @Singleton
    @Provides
    @Named("Files Dir")
    fun provideFilesDir(app: Application): File {
        return app.filesDir
    }

    @Provides
    fun provideStorageVolumes(app: Application): Array<File> {
        val storageManager = app.getSystemService(Context.STORAGE_SERVICE) as StorageManager
        return app.getExternalFilesDirs(null).filter { file ->
            val storageVolume: StorageVolume? = storageManager.getStorageVolume(file)
            storageVolume != null
        }.map { File(it.absolutePath.substringBefore("/Android")) }.toTypedArray()
    }

    @Provides
    fun provideBookReadingMiddleware(
        readingUseCases: ReadingUseCases
    ): BookReadingMiddleware {
        return BookReadingMiddleware(
            readingUseCases
        )
    }

    @Provides
    fun provideCleanupMiddleware(
        @Named("Files Dir")
        filesDir: File
    ): CleanupMiddleware {
        return CleanupMiddleware(
            filesDir
        )
    }

    @Provides
    fun provideTTSMiddleware(
        tts: TextToSpeech
    ): TTSMiddleware {
        return TTSMiddleware(
            tts
        )
    }

    @Singleton
    @Provides
    fun providePdfiumCore(app: Application): PdfiumCore {
        return PdfiumCore(app.applicationContext)
    }
}