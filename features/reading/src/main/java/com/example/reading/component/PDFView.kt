package com.example.reading.component

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import com.example.reader.ReadativeBookContent
import com.example.reader.ReadativePage
import com.example.reader.pdf.PdfBook
import com.example.reading.ReadingUiState
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.android.gms.tasks.Tasks
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PDFView(
    modifier: Modifier = Modifier,
    source: ReadativeBookContent.PDFContents,
    uiState: ReadingUiState,
    horizontal: Boolean = false
) {
    val pagerState = rememberPagerState(uiState.readingProgress.value.currentPage - 1)

    LaunchedEffect(pagerState) {
        // Set page number on page changed
        snapshotFlow { pagerState.currentPage }.collect { page ->
            if (page != uiState.readingProgress.value.currentPage - 1) {
                uiState.readingProgress.value =
                    uiState.readingProgress.value.copy(currentPage = page + 1)
            }
        }
    }

    LaunchedEffect(pagerState) {
        uiState.readingProgress.value =
            uiState.readingProgress.value.copy(pageCount = source.contents.pageCount)

        // Scroll to page on page number changed
        snapshotFlow { uiState.readingProgress.value.currentPage }.collect { page ->
            if (pagerState.currentPage != page - 1) {
                pagerState.scrollToPage(page - 1)
            }
        }
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { uiState.playTTS.value }.collect { playTTS ->
            if (playTTS) {
                if (uiState.job == null) {
                    getTextContents(source.contents, uiState)
                    uiState.startTTS()
                } else {
                    uiState.resumeTTS()
                }
            } else {
                uiState.pauseTTS()
            }
        }
    }

    if (horizontal) {
        HorizontalPager(
            modifier = modifier,
            count = source.contents.pageCount,
            state = pagerState
        ) { page ->
            ZoomableImage(
                bitmap = (source.contents
                    .getPage(page, uiState.screenWidth, uiState.screenHeight)
                        as ReadativePage.ImagePage
                        ).bitmap
            )
        }
    } else {
        VerticalPager(
            modifier = modifier,
            count = source.contents.pageCount,
            state = pagerState
        ) { page ->
            ZoomableImage(
                bitmap = (source.contents
                    .getPage(page, uiState.screenWidth, uiState.screenHeight)
                        as ReadativePage.ImagePage
                        ).bitmap
            )
        }
    }
}

@Composable
fun ZoomableImage(
    modifier: Modifier = Modifier,
    bitmap: Bitmap
) {
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, _ ->
        scale *= zoomChange
        offset += offsetChange
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .transformable(state = state)
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer(
                    // adding some zoom limits (min 100%, max 200%)
                    scaleX = maxOf(1f, minOf(3f, scale)),
                    scaleY = maxOf(1f, minOf(3f, scale)),
                    translationX = maxOf(0f, offset.x),
                    translationY = offset.y
                ),
            contentDescription = null,
            bitmap = bitmap.asImageBitmap()
        )
    }
}

fun getTextContents(
    content: PdfBook,
    uiState: ReadingUiState
) {
    uiState.job = uiState.coroutineScope.launch {
        withContext(Dispatchers.IO) {
            val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
            for (ind in 0 until content.pageCount) {
                val bm = (content.getPage(
                    ind,
                    uiState.screenWidth,
                    uiState.screenHeight
                ) as ReadativePage.ImagePage).bitmap
                val image = InputImage.fromBitmap(bm, 0)
                val result = recognizer.process(image)
                    .addOnSuccessListener { visionText ->
                        uiState.addToTTSQueue(visionText.text)
                    }
                    .addOnFailureListener { e ->
                        e.localizedMessage?.let { Log.e("MLKIT", it) }
                    }
                Tasks.await(result)
            }
        }
    }
}