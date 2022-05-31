package com.example.reading.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import com.example.reading.service.AudioBookService

class TTSInitListener(
): TextToSpeech.OnInitListener {
    override fun onInit(status: Int) {
        if (status == TextToSpeech.ERROR) {
            Log.e("TextToSpeech", "Initialization Failed!")
        }
    }
}