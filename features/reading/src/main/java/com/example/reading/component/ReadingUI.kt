package com.example.reading.component

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.reading.ReadingUiState
import com.example.reading.util.ReadingProgress
import kotlin.math.roundToInt


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ReadingScaffold(
    uiState: ReadingUiState,
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Scaffold(
        modifier = Modifier.clickable(
            indication = null,
            interactionSource = interactionSource
        ) {
            uiState.showUi.value = !uiState.showUi.value
        },
        scaffoldState = uiState.scaffoldState,
        bottomBar = {
            if (uiState.showUi.value) {
                ReadingBottomBar(
                    uiState = uiState
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(1f)
        ) {
            content()
            if  (uiState.showUi.value) {
                ReadingTopBar(
                    modifier = Modifier.fillMaxWidth(1f),
                    uiState
                )
            }
        }
    }
}

@Composable
fun ReadingTopBar(
    modifier: Modifier = Modifier,
    uiState: ReadingUiState
) {
    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = { uiState.navController.popBackStack() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
            }
        },
        title = {},
        actions = {
            IconButton(onClick = { uiState.horizontalView.value = !uiState.horizontalView.value }) {
                Icon(imageVector = Icons.Filled.RotateRight, contentDescription = null)
            }
            IconButton(onClick = { uiState.prevTTS(5) }) {
                Icon(imageVector = Icons.Filled.SkipPrevious, contentDescription = null)
            }
            IconButton(onClick = { uiState.playTTS.value = !uiState.playTTS.value }) {
                if (uiState.playTTS.value) {
                    Icon(imageVector = Icons.Filled.Pause, contentDescription = null)
                } else {
                    Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = null)
                }
            }
            IconButton(onClick = { uiState.nextTTS(5) }) {
                Icon(imageVector = Icons.Filled.SkipNext, contentDescription = null)
            }
        }
    )
}

@Composable
fun ReadingBottomBar(
    modifier: Modifier = Modifier,
    uiState: ReadingUiState
) {
    BottomAppBar(
        modifier = modifier
    ) {
        Column(
            Modifier.fillMaxWidth(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PageSlider(
                readingProgress = uiState.readingProgress.value,
                onValueChange = {uiState.readingProgress.value = uiState.readingProgress.value.copy(currentPage = it)}
            )
        }
    }
}

@Composable
fun PageSlider(
    readingProgress: ReadingProgress,
    onValueChange: (Int) -> Unit
) {
    Text(text = "${readingProgress.currentPage} of ${readingProgress.pageCount}")
    Slider(
        value = readingProgress.currentPage.toFloat(),
        valueRange = 1f..readingProgress.pageCount.toFloat(),
        onValueChange = { onValueChange(it.roundToInt()) },
        colors = SliderDefaults.colors(
            thumbColor = MaterialTheme.colors.secondary,
            activeTrackColor = MaterialTheme.colors.secondary
        )
    )
}