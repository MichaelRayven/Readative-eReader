package com.example.reader

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.os.ParcelFileDescriptor.MODE_READ_ONLY
import android.os.ParcelFileDescriptor.MODE_READ_WRITE
import com.example.model.local.util.BookFormat
import com.example.reader.epub.EpubBook
import com.example.reader.pdf.PdfBook
import java.io.File

class ReadativeBookReader(
    private val context: Context
) {
    fun readBook(file: File): ReadativeBook? {
        val format = BookFormat.values().firstOrNull { file.extension == it.extension }
        return when (format) {
            BookFormat.EPUB -> {
                EpubBook(context.filesDir.toPath(), context, null, file)
            }
            BookFormat.PDF -> {
                PdfBook(context.filesDir.toPath(), context, null, file)
            }
            else -> null
        }
    }

    fun readBook(uri: Uri, format: BookFormat?): ReadativeBook? {
        return when (format) {
            BookFormat.EPUB -> {
                EpubBook(context.filesDir.toPath(), context, uri, null)
            }
            BookFormat.PDF -> {
                PdfBook(context.filesDir.toPath(), context, uri, null)
            }
            else -> null
        }
    }
}