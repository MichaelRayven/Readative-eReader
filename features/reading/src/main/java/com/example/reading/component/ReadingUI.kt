package com.example.reading.component

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.reading.util.ProgressHolder
import kotlin.math.roundToInt


@Composable
fun ReadingOverlay(
    onPlayClick: () -> Unit,
    currentPage: MutableState<Int>,
    pageCount: MutableState<Int>,
    content: @Composable (Modifier) -> Unit
) {
    var showUi by remember { mutableStateOf(true) }
    val interactionSource = remember { MutableInteractionSource() }

    ConstraintLayout(
        modifier = Modifier.clickable(
            indication = null,
            interactionSource = interactionSource
        ) {
            showUi = !showUi
        }
    ) {
        val (readingTopBar, readingBottomBar, readingView) = createRefs()

        content(
            Modifier
                .constrainAs(readingView) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .fillMaxSize(1f))
        if (showUi) {
            ReadingTopBar(Modifier.constrainAs(readingTopBar) {
                top.linkTo(parent.top)
            }, onPlayClick)
            ReadingBottomBar(
                modifier = Modifier.constrainAs(readingBottomBar) {
                    bottom.linkTo(parent.bottom)
                },
                value = currentPage.value.toFloat(),
                valueRange = 1f..pageCount.value.toFloat()
//                onValueChange = { currentPage.value = it.roundToInt() }
            )
        }
    }
}

@Composable
fun ReadingTopBar(
    modifier: Modifier = Modifier,
    onPlayClick: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
            }
        },
        title = {},
        actions = {
            IconButton(onClick = onPlayClick) {
                Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = null)
            }
        }
    )
}

@Composable
fun ReadingBottomBar(
    modifier: Modifier = Modifier,
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>
//    onValueChange: (Float) -> Unit
) {
    BottomAppBar(
        modifier = modifier
    ) {
        Column(
            Modifier.fillMaxWidth(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "${value.toInt()} of ${valueRange.endInclusive.toInt()}")
//            Slider(
//                value = value,
//                onValueChange = onValueChange,
//                valueRange = valueRange,
//                colors = SliderDefaults.colors(
//                    thumbColor = MaterialTheme.colors.secondary,
//                    activeTrackColor = MaterialTheme.colors.secondary
//                )
//            )
        }
    }
}