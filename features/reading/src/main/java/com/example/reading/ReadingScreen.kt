package com.example.reading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reading.component.ReadingView
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.web.WebView

@Composable
fun ReadingScreen(
    bookId: Long,
    viewModel: ReadingViewModel = hiltViewModel()
) {
    LaunchedEffect(true) {
        viewModel.bookOpened(bookId)
    }
    val state: ReadingState = viewModel.viewState.collectAsState().value

    when (state) {
        is ReadingState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
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
            LazyColumn {
                items(state.pages) {
                    ReadingView(it)
                }
            }
        }
    }
}