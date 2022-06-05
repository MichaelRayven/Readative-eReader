package com.example.reading

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import com.example.framework.mvi.Middleware
import com.example.framework.mvi.Store
import com.example.usecase.reading.ReadingUseCases
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.single
import java.io.File

class TTSMiddleware(
    private val tts: TextToSpeech
) : Middleware<ReadingState, ReadingAction>, UtteranceProgressListener() {
    private val channel = Channel<Unit>(0)
    private var notified = false

    private var currentTextId = 0

    private val sentences = mutableListOf<String>()

    override suspend fun process(
        action: ReadingAction,
        currentState: ReadingState,
        store: Store<ReadingState, ReadingAction>
    ) {
        when (action) {
            is ReadingAction.TextToSpeechAdd -> {
                addToTTS(store, action)
            }
            is ReadingAction.TextToSpeechReset -> {
                resetTTS()
            }
            is ReadingAction.TextToSpeechPause -> {
                pauseTTS()
            }
            is ReadingAction.TextToSpeechResume -> {
                resumeTTS()
            }
            is ReadingAction.TextToSpeechStart -> {
                startTTS()
            }
            is ReadingAction.TextToSpeechNextClicked -> {
                nextTTS(action)
            }
            is ReadingAction.TextToSpeechPrevClicked -> {
                prevTTS(action)
            }
            else -> {}
        }
    }

    private fun resetTTS() {
        currentTextId = 0
        tts.stop()
    }

    private suspend fun startTTS() {
        wait()
        currentTextId = 0
        if (sentences.size > 0) {
            tts.speak(
                sentences[0],
                TextToSpeech.QUEUE_ADD,
                Bundle(),
                0.toString()
            )
        }
    }

    private fun addToTTS(
        store: Store<ReadingState, ReadingAction>,
        action: ReadingAction.TextToSpeechAdd
    ) {
        tts.setOnUtteranceProgressListener(this@TTSMiddleware)

        val texts = action.text.split(Regex("(?<=[.!?:]\\s)"))
        sentences.addAll(texts)
        notifyChannel()
    }

    private fun pauseTTS() {
        currentTextId = (currentTextId- 1).coerceAtLeast(0)
        tts.stop()
    }

    private fun resumeTTS() {
        if (sentences.size > currentTextId) {
            tts.speak(
                sentences[currentTextId],
                TextToSpeech.QUEUE_ADD,
                Bundle(),
                currentTextId.toString()
            )
        }
    }

    private fun nextTTS(action: ReadingAction.TextToSpeechNextClicked) {
        currentTextId = maxOf(0, minOf(currentTextId + action.count - 1, sentences.size - 1))
        tts.stop()
        resumeTTS()
    }

    private fun prevTTS(action: ReadingAction.TextToSpeechPrevClicked) {
        currentTextId = maxOf(0, minOf(currentTextId - action.count - 1, sentences.size - 1))
        tts.stop()
        resumeTTS()
    }

    override fun onStart(utteranceId: String?) {
    }

    override fun onDone(utteranceId: String?) {
        currentTextId += 1
        if (sentences.size > currentTextId) {
            tts.speak(
                sentences[currentTextId],
                TextToSpeech.QUEUE_ADD,
                Bundle(),
                currentTextId.toString()
            )
        } else {
            pauseTTS()
        }
    }

    override fun onError(utteranceId: String?) {
        Log.e("TTS", "Error")
    }

    suspend fun wait() {
        channel.receive()
    }

    fun notifyChannel() {
        channel.trySend(Unit).isSuccess
    }
}