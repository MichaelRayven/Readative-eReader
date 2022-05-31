package com.example.reader

import android.graphics.Bitmap

sealed class ReadativeBookContent {
    data class PDFContents(
        val contents: List<Bitmap>
    ) : ReadativeBookContent()
    data class EPUBContents(
        val contents: String,
        val baseUrl: String
    ) : ReadativeBookContent()
}