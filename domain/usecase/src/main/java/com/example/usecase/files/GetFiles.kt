package com.example.usecase.files

import com.example.model.dto.ArchiveDto
import com.example.model.dto.BasicBookDto
import com.example.model.dto.FolderDto
import com.example.model.dto.extension.toBasicBookDto
import com.example.model.local.util.BookFormat
import com.example.repository.BookRepository
import com.example.repository.FileRepository
import kotlinx.coroutines.flow.onEach
import java.io.File

class GetFiles(
    private val bookRepository: BookRepository,
    private val fileRepository: FileRepository
) {
    suspend operator fun invoke(path: String): Triple<List<BasicBookDto>, List<FolderDto>, List<ArchiveDto>> {
        val books = mutableListOf<BasicBookDto>()
        val folders = mutableListOf<FolderDto>()
        val archives = mutableListOf<ArchiveDto>()
        val folder = File(path)

        folder.listFiles()?.forEach { file ->
            if (!file.isHidden) {
                if (file.isDirectory) {
                    val count = fileRepository.getAllByPath(file.path).size
                    folders.add(FolderDto(file.name, count))
                } else {
                    val format = BookFormat.extensionsMap[file.extension]
//                      val archiveFormat = ArchiveFormat.extensionsMap[file.extension]
                    if (format != null) {
                        val dbFile = fileRepository.getByPath(file.path)
                        val bookWithInfo = dbFile?.bookId?.let {
                            bookRepository.getByIdWithBasicInfo(
                                it
                            )
                        }
                        bookWithInfo?.toBasicBookDto()?.let { books.add(it) }
                    }
                    /*else if (archiveFormat != null) {
                            var count = 0
                            fileRepository.getAllByPath(file.path).onEach { count = it.size }
                            archives.add(ArchiveDto(file.name, count, archiveFormat))
                        }*/
                }
            }

        }
        return Triple(books, folders, archives)
    }
//
//    private suspend fun processFolder(file: File): FolderDto {
//        return withContext(IO) {
//            var count = 0
//            file.walkTopDown().forEach { fileInner ->
//                if (!fileInner.isHidden && fileInner.isBook) count++
//            }
//            FolderDto(file.name, count)
//        }
//    }
//
//    private suspend fun processBook(file: File, format: BookFormat): BasicBookDto? {
//        return withContext(IO) {
//            val dbBookFile = fileRepository.getByPath(file.path)
//            val dbBook = dbBookFile?.let { bookRepository.getByIdWithBasicInfo(it.bookId) }
//            dbBook?.toBasicBookDto()
//        }
//    }
//
//    private suspend fun processArchive(file: File, format: ArchiveFormat): ArchiveDto {
//        return ArchiveDto(file.name, countBooksInArchive(file), format)
//    }
//
//    private suspend fun countBooksInArchive(file: File): Int {
//        return withContext(IO) {
//            var count = 0
//            val archive = Archive.Builder().setSource(file).build() ?: return@withContext 0
//
//            when (val input = archive.getArchiveInputStream()) {
//                is com.github.junrar.Archive -> {
//                    archive.entries(input).forEach { entry ->
//                        launch {
//                            if (!entry.isDirectory) {
//                                if (entry.isBook) {
//                                    count++
//                                } else if (entry.isArchive) {
//                                    val temp = tempDirectory(file)
//                                    // Extract the archive
//                                    archive.extractEntry(temp, input, entry)
//                                    // Count books inside
//                                    count += countBooksInArchive(file)
//                                }
//                            }
//                        }
//                    }
//                }
//                is ArchiveInputStream -> {
//                    archive.entries(input).forEach { entry ->
//                        launch {
//                            if (!entry.isDirectory) {
//                                if (entry.isBook) {
//                                    count++
//                                } else if (entry.isArchive) {
//                                    val temp = tempDirectory(file)
//                                    // Extract the archive
//                                    archive.extractEntry(temp, input, entry)
//                                    // Count books inside
//                                    count += countBooksInArchive(file)
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            count
//        }
//    }
//
//    private fun tempDirectory(file: File): File {
//        val count = namespaceData[file.nameWithoutExtension] ?: 0
//
//        // Create path
//        val path = Paths.get(
//            filesDir.absolutePath,
//            "temp",
//            "${file.nameWithoutExtension}-$count"
//        )
//
//        // Change namespace data
//        if (count == 0) {
//            namespaceData[file.nameWithoutExtension] = 1
//        } else {
//            namespaceData[file.nameWithoutExtension] = count + 1
//        }
//
//        return path.toFile()
//    }
}
//
//private val ArchiveEntry.isBook: Boolean
//    get() = BookFormat.values().any { it.extension == this.extension }
//
//private val File.isBook: Boolean
//    get() = BookFormat.values().any { it.extension == this.extension }
//
//private val FileHeader.isBook: Boolean
//    get() = BookFormat.values().any { it.extension == this.extension }
//
//private val ArchiveEntry.isArchive: Boolean
//    get() = ArchiveFormat.values().any { it.extension == this.extension }
//
//private val File.isArchive: Boolean
//    get() = ArchiveFormat.values().any { it.extension == this.extension }
//
//private val FileHeader.isArchive: Boolean
//    get() = ArchiveFormat.values().any { it.extension == this.extension }
//
//private val ArchiveEntry.extension: String
//    get() = this.name.substringAfter('.')
//
//private val FileHeader.extension: String
//    get() = this.fileName.substringAfter('.')