package com.example.reading.component

import androidx.compose.runtime.*
import com.example.reader.ReadativeBookContent
import com.example.reading.ReadingUiState

@Composable
fun ReadingView(
    source: ReadativeBookContent,
    uiState: ReadingUiState
) {
    ReadingScaffold(
        uiState = uiState
    ) {
        when (source) {
            is ReadativeBookContent.EPUBContents -> {
                EPUBView(
                    source = source,
                    uiState = uiState
                )
            }
            is ReadativeBookContent.PDFContents -> {
                PDFView(
                    source = source,
                    uiState = uiState,
                    horizontal = uiState.horizontalView.value
                )
            }
        }
    }
}