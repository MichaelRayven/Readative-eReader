package com.example.reading

import android.speech.tts.TextToSpeech
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavController
import com.example.reading.util.ReadingProgress
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

data class ReadingUiState(
    val scaffoldState: ScaffoldState,
    val navController: NavController,
    val coroutineScope: CoroutineScope,
    val actionFlow: MutableSharedFlow<ReadingAction>,
    val screenWidth: Int,
    val screenHeight: Int
) {
    var job: Job? = null

    var playTTS = mutableStateOf(false)

    var showUi = mutableStateOf(false)

    var readingProgress = mutableStateOf(ReadingProgress(1,1))

    var horizontalView = mutableStateOf(false)

    fun addToTTSQueue(text: String) {
        actionFlow.tryEmit(ReadingAction.TextToSpeechAdd(text))
    }

    fun resetTTSQueue() {
        actionFlow.tryEmit(ReadingAction.TextToSpeechReset)
    }

    fun pauseTTS() {
        actionFlow.tryEmit(ReadingAction.TextToSpeechPause)
    }

    fun resumeTTS() {
        actionFlow.tryEmit(ReadingAction.TextToSpeechResume)
    }

    fun startTTS() {
        actionFlow.tryEmit(ReadingAction.TextToSpeechStart)
    }

    fun nextTTS(count: Int) {
        actionFlow.tryEmit(ReadingAction.TextToSpeechNextClicked(count))
    }

    fun prevTTS(count: Int) {
        actionFlow.tryEmit(ReadingAction.TextToSpeechPrevClicked(count))
    }

    fun showSnackbar(message: String) {
        coroutineScope.launch {
            scaffoldState.snackbarHostState.showSnackbar(
                message
            )
        }
    }
}