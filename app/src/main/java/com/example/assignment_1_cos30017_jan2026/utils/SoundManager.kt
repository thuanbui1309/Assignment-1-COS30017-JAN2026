package com.example.assignment_1_cos30017_jan2026.utils

import android.content.Context
import android.media.MediaPlayer

object SoundManager {

    private var mediaPlayer: MediaPlayer? = null

    private fun playSound(context: Context, resId: Int) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(context, resId).apply {
            setOnCompletionListener { it.release() }
            start()
        }
    }

    fun playPerformSound(context: Context) {
        playSound(context, com.example.assignment_1_cos30017_jan2026.R.raw.sound_success)
    }

    fun playDeductionSound(context: Context) {
        playSound(context, com.example.assignment_1_cos30017_jan2026.R.raw.sound_fail)
    }

    fun playFinishSound(context: Context) {
        playSound(context, com.example.assignment_1_cos30017_jan2026.R.raw.sound_win)
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
