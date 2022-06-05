package com.example.reading.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log

private const val ACTION_PLAY: String = "com.example.action.PLAY"

class AudioBookService: Service(), MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {
    private val binder = LocalBinder()
    private var mediaPlayer: MediaPlayer? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        when(intent.action) {
            ACTION_PLAY -> {
                mediaPlayer = MediaPlayer.create(this, intent.getParcelableExtra(URI_EXTRA))
                initMediaPlayer(mediaPlayer)
            }
        }
        return START_STICKY_COMPATIBILITY
    }

    private fun initMediaPlayer(mp: MediaPlayer?) {
        mp?.apply {
            setOnPreparedListener(this@AudioBookService)
            setOnErrorListener(this@AudioBookService)
            prepareAsync()
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mp?.start()
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        mp?.reset()
        initMediaPlayer(mp)
        Log.e("MediaPlayer", "Error code: $extra")
        return true
    }

    inner class LocalBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods
        fun getService(): AudioBookService = this@AudioBookService
    }

    companion object {
        const val URI_EXTRA: String = "audio_uri"
    }
}