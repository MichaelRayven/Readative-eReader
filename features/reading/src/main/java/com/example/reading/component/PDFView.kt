package com.example.reading.component

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.reader.ReadativeBookContent
import com.example.reading.util.ProgressHolder
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.StringBuilder

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PDFView(
    modifier: Modifier = Modifier,
    source: ReadativeBookContent.PDFContents,
    currentPage: MutableState<Int>,
    pageCount: MutableState<Int>,
    collectText: Boolean,
    textInput: MutableState<String>,
    horizontal: Boolean = false
) {
    val pagerState = rememberPagerState(currentPage.value - 1)
    var canCollectText by remember { mutableStateOf(true) }
    val configuration = LocalConfiguration.current

    LaunchedEffect(pagerState) {
        pageCount.value = source.contents.size
//        snapshotFlow { currentPage.value }.collect { page ->
//            pagerState.scrollToPage(page - 1)
//        }
        snapshotFlow { pagerState.currentPage }.collect { page ->
            currentPage.value = page + 1
        }
    }

    if (collectText && canCollectText) {
        canCollectText = false
        LaunchedEffect(true) {
            getTextContents(source.contents, textInput)
        }
    }

    if (horizontal) {
        HorizontalPager(
            count = source.contents.size,
            state = pagerState,
            modifier = modifier
        ) { page ->
            Image(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(configuration.screenHeightDp.dp),
                bitmap = source.contents[page].asImageBitmap(),
                contentDescription = null
            )
        }
    } else {
        VerticalPager(
            count = source.contents.size,
            state = pagerState,
            modifier = modifier
        ) { page ->
            Image(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(configuration.screenHeightDp.dp),
                bitmap = source.contents[page].asImageBitmap(),
                contentDescription = null
            )
        }
    }
}

suspend fun getTextContents(
    images: List<Bitmap>,
    textInput: MutableState<String>
) {
    withContext(Dispatchers.IO) {
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        val content = StringBuilder()
        images.forEach { bm ->
            val image = InputImage.fromBitmap(bm, 0)
            val result = recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    content.append(' ', visionText.text)
                }
                .addOnFailureListener { e ->
                    e.localizedMessage?.let { Log.e("MLKIT", it) }
                }
            Tasks.await(result)
        }
        textInput.value = content.toString()
    }
}