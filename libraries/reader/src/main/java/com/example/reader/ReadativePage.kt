package com.example.reader

import android.graphics.Bitmap
import java.nio.file.Path

sealed class ReadativePage {
    data class ImagePage(val bitmap: Bitmap): ReadativePage()
    data class HtmlPage(val url: String): ReadativePage()
}