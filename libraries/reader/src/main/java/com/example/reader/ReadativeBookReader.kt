package com.example.reader

import android.content.Context
import android.net.Uri
import com.example.model.local.util.BookFormat
import com.example.reader.epub.EpubBook
import com.example.reader.pdf.PdfBook
import com.shockwave.pdfium.PdfiumCore
import java.io.File

class ReadativeBookReader(
    private val context: Context,
    private val pdfiumCore: PdfiumCore
) {
    fun readBook(file: File): ReadativeBook? {
        return readBook(
            Uri.fromFile(file),
            BookFormat.extensionsMap[file.extension]
        )
    }

    fun readBook(uri: Uri, format: BookFormat?): ReadativeBook? {
        return when (format) {
            BookFormat.EPUB -> {
                EpubBook(context, uri)
            }
            BookFormat.PDF -> {
                PdfBook(context, pdfiumCore, uri)
            }
            else -> null
        }
    }
}