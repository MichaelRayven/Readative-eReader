package com.example.reading.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import com.example.reader.ReadativePage
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState

@Composable
fun ReadingView(
    page: ReadativePage
) {
    when (page) {
        is ReadativePage.ImagePage -> {
            Image(
                bitmap = page.bitmap.asImageBitmap(),
                contentDescription = null
            )
        }
        is ReadativePage.HtmlPage -> {
            val state = rememberWebViewState(page.url)
            WebView(
                state = state,
                onCreated = { it.settings.allowFileAccess = true },
                captureBackPresses = false
            )
        }
    }
}