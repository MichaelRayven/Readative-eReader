package com.example.archives

import com.example.model.local.util.ArchiveFormat
import com.example.model.local.util.BookFormat
import com.github.junrar.Archive
import com.github.junrar.rarfile.FileHeader
import org.apache.commons.compress.archivers.ArchiveEntry

class ArchiveEntry {
    val entry: Any
    val name: String
        get() {
            return when(entry) {
                is ArchiveEntry -> entry.name
                is FileHeader -> entry.fileName
                else -> ""
            }
        }
    val size: Long
        get() {
            return when(entry) {
                is ArchiveEntry -> entry.size
                is FileHeader -> entry.fullUnpackSize
                else -> 0
            }
        }
    val extension: String
        get() {
            return when(entry) {
                is ArchiveEntry -> entry.name.substringAfterLast('.', "")
                is FileHeader -> entry.fileName.substringAfterLast('.', "")
                else -> ""
            }
        }
    val isDirectory: Boolean
        get() {
            return when(entry) {
                is ArchiveEntry -> entry.isDirectory
                is FileHeader -> entry.isDirectory
                else -> false
            }
        }
    val isArchive: Boolean
        get() {
            val extension = this.extension
            return if (extension.isNotBlank()) {
                ArchiveFormat.values().any { it.extension == extension }
            } else false
        }
    val isBook: Boolean
        get() {
            val extension = this.extension
            return if (extension.isNotBlank()) {
                BookFormat.values().any { it.extension == extension }
            } else false
        }

    constructor(entry: ArchiveEntry) {
        this.entry = entry
    }
    constructor(entry: FileHeader) {
        this.entry = entry
    }
}