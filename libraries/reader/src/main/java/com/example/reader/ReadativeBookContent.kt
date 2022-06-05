package com.example.reader

import android.graphics.Bitmap
import com.example.reader.pdf.PdfBook

sealed class ReadativeBookContent {
    data class PDFContents(
        val contents: PdfBook
    ) : ReadativeBookContent()
    data class EPUBContents(
        val contents: String,
        val baseUrl: String
    ) : ReadativeBookContent()
}