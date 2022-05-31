package com.example.reading

import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import com.example.reader.ReadativeBookContent
import com.example.reading.component.EPUBView
import com.example.reading.component.PDFView
import com.example.reading.component.ReadingOverlay
import com.example.theme.R
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun ReadingScreen(
    bookId: Long,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    viewModel: ReadingViewModel = hiltViewModel()
) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val state: ReadingState = viewModel.viewState.collectAsState().value

    DisposableEffect(lifecycleOwner) {
        val screenHeight = with(density) { configuration.screenHeightDp.dp.toPx() }.toInt()
        val screenWidth = with(density) { configuration.screenWidthDp.dp.toPx() }.toInt()
        viewModel.bookOpened(bookId, screenWidth, screenHeight)

        onDispose {
            viewModel.bookClosed()
        }
    }

    when (state) {
        is ReadingState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.loading),
                    style = MaterialTheme.typography.subtitle1
                )
                Spacer(modifier = Modifier.height(16.dp))
                LinearProgressIndicator()
            }
        }
        is ReadingState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(state.message)
            }
        }
        is ReadingState.Loaded -> {
            ReadingView(source = state.source) { text ->
                viewModel.ttsPlayClicked(bookId, text)
            }
        }
    }
}

@Composable
fun ReadingView(
    source: ReadativeBookContent,
    onTextLoaded: (String) -> Unit
) {
    var collectTextTrigger by remember {
        mutableStateOf(false)
    }
    val rawText = remember {
        mutableStateOf("")
    }
    val currentPage = remember {
        mutableStateOf(1)
    }
    val pageCount = remember {
        mutableStateOf(1)
    }

    LaunchedEffect(true) {
        snapshotFlow { rawText.value }.onEach { text ->
            if (collectTextTrigger) {
                onTextLoaded(text)
            }
        }.launchIn(MainScope())
    }

    ReadingOverlay(
        currentPage = currentPage,
        pageCount = pageCount,
        onPlayClick = {
            collectTextTrigger = true
        }
    ) {
        when (source) {
            is ReadativeBookContent.EPUBContents -> {
                EPUBView(
                    modifier = it,
                    source = source,
                    pageCount = pageCount,
                    currentPage = currentPage,
                    collectText = collectTextTrigger,
                    textInput = rawText
                )
            }
            is ReadativeBookContent.PDFContents -> {
                PDFView(
                    modifier = it,
                    source = source,
                    pageCount = pageCount,
                    currentPage = currentPage,
                    collectText = collectTextTrigger,
                    textInput = rawText
                )
            }
        }
    }
}