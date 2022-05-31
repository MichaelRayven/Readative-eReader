package com.example.reading

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import com.example.framework.mvi.Middleware
import com.example.framework.mvi.Store
import com.example.reading.service.AudioBookService
import com.example.usecase.reading.ReadingUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.*

class TTSMiddleware(
    private val readingUseCases: ReadingUseCases,
    private val tts: TextToSpeech,
    private val filesDir: File,
    private val context: Context
) : Middleware<ReadingState, ReadingAction>, UtteranceProgressListener() {
    lateinit var audioFile: File

    override suspend fun process(
        action: ReadingAction,
        currentState: ReadingState,
        store: Store<ReadingState, ReadingAction>
    ) {
        when (action) {
            is ReadingAction.TextToSpeechPlayClicked -> {
                runTTS(store, action)
            }
            else -> {}
        }
    }

    private suspend fun runTTS(
        store: Store<ReadingState, ReadingAction>,
        action: ReadingAction.TextToSpeechPlayClicked
    ) {
        withContext(Dispatchers.IO) {
//            audioFile = readingUseCases.getBook(action.id)?.book?.checksum?.let {
//                filesDir.resolve("audio").resolve("$it.wav")
//            } ?: throw Exception("Called Text-To-Speech on uninitialized document")
//            audioFile.parentFile?.mkdirs()
//            if (!audioFile.exists()) {
//                tts.setOnUtteranceProgressListener(this@TTSMiddleware)
//                tts.language = Locale.ENGLISH
//
//                val bundleTts = Bundle()
//                bundleTts.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, audioFile.name)
//
//                tts.synthesizeToFile(
//                    "111111111",
//                    bundleTts,
//                    audioFile,
//                    audioFile.name
//                )
//            }
            tts.speak(
                "",
                TextToSpeech.QUEUE_FLUSH,
                Bundle(),
                "0"
            )
            tts.setOnUtteranceProgressListener(this@TTSMiddleware)
            tts.language = Locale.US

            val texts = action.text.split(Regex("(?<=\\.)"))
            for (i in texts.indices) {
                tts.speak(
                    texts[i],
                    TextToSpeech.QUEUE_ADD,
                    Bundle(),
                    i.toString()
                )
            }
        }
    }

    override fun onStart(utteranceId: String?) {
    }

    override fun onDone(utteranceId: String?) {
//        Intent(context, AudioBookService::class.java).also { intent ->
//            intent.putExtra(AudioBookService.URI_EXTRA, Uri.fromFile(audioFile))
//            context.startService(intent)
//        }
    }

    override fun onError(utteranceId: String?) {
        Log.e("TTS", "Error")
    }
}