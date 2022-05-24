package com.example.archives

import android.content.Context
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.os.ParcelFileDescriptor.MODE_READ_ONLY
import com.example.model.local.util.ArchiveFormat
import com.github.junrar.rarfile.FileHeader
import org.apache.commons.compress.archivers.ArchiveEntry
import org.apache.commons.compress.archivers.ArchiveInputStream
import org.apache.commons.compress.archivers.ArchiveStreamFactory
import org.apache.commons.compress.compressors.CompressorException
import org.apache.commons.compress.compressors.CompressorStreamFactory
import org.apache.commons.compress.utils.IOUtils
import java.io.*
import java.nio.file.Files

class Archive private constructor(
    private val file: File?,
    private val uri: Uri?,
    private val context: Context?,
    private val format: ArchiveFormat,
) {
    fun getArchiveInputStream(): ArchiveInput {
        val fis = if (uri != null && context != null) {
            context.contentResolver?.openInputStream(uri)
        } else {
            FileInputStream(file)
        }
        val bis = BufferedInputStream(fis)

        return if (format == ArchiveFormat.RAR) {
            ArchiveInput(com.github.junrar.Archive(fis))
        } else {
            val cis = try {
                CompressorStreamFactory().createCompressorInputStream(bis)
            } catch (e: CompressorException) {
                null
            }

            if (cis != null) {
                ArchiveInput(ArchiveStreamFactory().createArchiveInputStream(cis))
            } else {
                ArchiveInput(ArchiveStreamFactory().createArchiveInputStream(bis))
            }
        }
    }

//    fun extractRecursively(dir: File): Boolean {
//        var result = true
//        if (extract(dir)) {
//            dir.listFiles()?.forEach { file ->
//                ArchiveFormat.values().firstOrNull { it.extension == file.extension }?.let {
//                    result = Builder()
//                        .setSource(ParcelFileDescriptor.open(file, MODE_READ_ONLY))
//                        .setFormat(it)
//                        .build()
//                        ?.extractRecursively(
//                            File(dir, file.nameWithoutExtension)
//                        ) == true
//                }
//            }
//        } else {
//            result = false
//        }
//        return result
//    }

    fun extractEntry(dir: File, archiveInput: ArchiveInput, archiveEntry: com.example.archives.ArchiveEntry): String? {
        val input = archiveInput.input
        val entry = archiveEntry.entry
        return if (input is ArchiveInputStream && entry is ArchiveEntry) {
            extractEntry(dir, input, entry)
        } else if (input is com.github.junrar.Archive && entry is FileHeader) {
            extractEntry(dir, input, entry)
        } else null
    }

    fun extractEntry(dir: File, input: ArchiveInputStream, entry: ArchiveEntry): String? {
        if (!input.canReadEntryData(entry)) return null
        val f = File(dir, entry.name ?: return null)

        // Protection against zip slip
        val destDirPath = dir.canonicalPath
        val destFilePath = f.canonicalPath

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw IOException("Entry is outside of target directory: ${entry.name}")
        }

        if (entry.isDirectory) {
            if (!f.isDirectory && !f.mkdirs()) {
                throw IOException("Failed to create directory $f")
            }
        } else {
            val parentDir = f.parentFile
            if (parentDir != null) {
                if (!parentDir.isDirectory && !parentDir.mkdirs()) {
                    throw IOException("Failed to create directory $dir")
                }
            }
            Files.newOutputStream(f.toPath()).use { o -> IOUtils.copy(input, o) }
        }
        return destFilePath
    }

    fun extractEntry(dir: File, input: com.github.junrar.Archive, entry: FileHeader): String? {
        val f = File(dir, entry.fileName ?: return null)

        // Protection against zip slip
        val destDirPath = dir.canonicalPath
        val destFilePath = f.canonicalPath

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw IOException("Entry is outside of target directory: ${entry.fileName}")
        }

        if (entry.isDirectory) {
            if (!f.isDirectory && !f.mkdirs()) {
                throw IOException("Failed to create directory $f")
            }
        } else {
            if (!dir.isDirectory && !dir.mkdirs()) {
                throw IOException("Failed to create directory $dir")
            }
            Files.newOutputStream(f.toPath()).use { o -> input.extractFile(entry, o) }
        }
        return destFilePath
    }

    fun extract(dir: File, archiveInput: ArchiveInput): Boolean {
        val input = archiveInput.input
        try {
            when(input) {
                is com.github.junrar.Archive -> {
                    var entry: FileHeader?
                    while ((input.nextFileHeader().also { entry = it }) != null) {
                        entry?.let { extractEntry(dir, input, it) }
                    }
                }
                is ArchiveInputStream -> {
                    var entry: ArchiveEntry?
                    while ((input.nextEntry.also { entry = it }) != null) {
                        entry?.let { extractEntry(dir, input, it) }
                    }
                }
            }
        } catch (e: Exception) {
            return false
        }
        return true
    }

    fun entries(archiveInput: ArchiveInput): Sequence<com.example.archives.ArchiveEntry>? {
        return when(val input = archiveInput.input) {
            is com.github.junrar.Archive -> {
                sequence {
                    var entry: FileHeader?
                    while ((input.nextFileHeader().also { entry = it }) != null) {
                        entry?.let { yield(ArchiveEntry(it)) }
                    }
                }
            }
            is ArchiveInputStream -> {
                sequence {
                    var entry: ArchiveEntry?
                    while ((input.nextEntry.also { entry = it }) != null) {
                        if (!input.canReadEntryData(entry)) continue

                        entry?.let { yield(ArchiveEntry(it)) }
                    }
                }
            }
            else -> null
        }
    }

    class Builder {
        private lateinit var format: ArchiveFormat
        private var uri: Uri? = null
        private var file: File? = null
        private var context: Context? = null

        fun setSource(uri: Uri): Builder {
            this.uri = uri
            return this
        }

        fun setContext(ctx: Context): Builder {
            this.context = ctx
            return this
        }

        fun setSource(file: File): Builder {
            this.file = file
            return this
        }

        fun setFormat(format: ArchiveFormat): Builder {
            this.format = format
            return this
        }

        fun build(): Archive? {
            return try {
                val fis = if (uri != null && context != null) {
                    context?.contentResolver?.openInputStream(uri!!)
                } else if (file != null) {
                    FileInputStream(file)
                } else return null

                val bis = BufferedInputStream(fis)

                if (format == ArchiveFormat.RAR) {
                    com.github.junrar.Archive(fis)
                } else {
                    ArchiveStreamFactory.detect(bis)
                }

                fis?.close()
                Archive(file, uri, context, format)
            } catch (e: Exception) {
                null
            }
        }
    }
}