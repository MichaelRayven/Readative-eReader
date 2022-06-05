package com.example.reading

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.reader.ReadativeBookContent
import com.example.reading.component.*
import com.example.reading.util.ReadingProgress
import com.example.theme.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun ReadingScreen(
    bookId: Long,
    navController: NavController,
    viewModel: ReadingViewModel = hiltViewModel()
) {
    val state: ReadingState = viewModel.viewState.collectAsState().value
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    val screenHeight = with(density) { configuration.screenHeightDp.dp.toPx() }.toInt()
    val screenWidth = with(density) { configuration.screenWidthDp.dp.toPx() }.toInt()

    val uiState = rememberReadingUiState(
        actionFlow = viewModel.actionFlow,
        navController = navController,
        screenHeight = screenHeight,
        screenWidth = screenWidth
    )

    LaunchedEffect(Unit) {
        viewModel.bookOpened(bookId, screenWidth, screenHeight)
    }

    DisposableEffect(lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_DESTROY -> {
                    viewModel.bookClosed()
                    uiState.job?.cancel()
                    uiState.resetTTSQueue()
                }
                else -> {}
            }
        }

        lifecycle.addObserver(observer)

        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    when (state) {
        is ReadingState.Loading -> {
            ReadingLoadingScreen()
        }
        is ReadingState.Error -> {
            ReadingErrorScreen(
                message = state.message,
                navController = navController
            )
        }
        is ReadingState.Loaded -> {
            ReadingView(
                source = state.source,
                uiState = uiState
            )
        }
    }
}

@Composable
fun ReadingLoadingScreen() {
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

@Composable
fun ReadingErrorScreen(
    message: String,
    navController: NavController
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.subtitle1
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text(
                text = "Go back",
                style = MaterialTheme.typography.subtitle2
            )
        }
    }
}

@Composable
fun rememberReadingUiState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    actionFlow: MutableSharedFlow<ReadingAction>,
    screenWidth: Int,
    screenHeight: Int
) = remember(scaffoldState, navController, coroutineScope, actionFlow, screenWidth, screenHeight) {
    ReadingUiState(
        scaffoldState = scaffoldState,
        navController = navController,
        coroutineScope = coroutineScope,
        actionFlow = actionFlow,
        screenWidth = screenWidth,
        screenHeight = screenHeight
    )
}