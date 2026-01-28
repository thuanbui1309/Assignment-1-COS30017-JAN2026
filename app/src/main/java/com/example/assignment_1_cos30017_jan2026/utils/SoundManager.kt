package com.example.assignment_1_cos30017_jan2026.utils

import android.media.AudioManager
import android.media.ToneGenerator

/**
 * Utility class to handle app sound effects using ToneGenerator.
 * Designed for "Gymmer" aesthetic with efficient, distinct beeps.
 */
object SoundManager {
    
    private val toneGen = ToneGenerator(AudioManager.STREAM_MUSIC, 100)

    /**
     * Short high beep for successful performance.
     */
    fun playPerformSound() {
        toneGen.startTone(ToneGenerator.TONE_PROP_BEEP, 150)
    }

    /**
     * Low buzz for deduction.
     */
    fun playDeductionSound() {
        toneGen.startTone(ToneGenerator.TONE_CDMA_SOFT_ERROR_LITE, 300)
    }

    /**
     * Distinct double-beep for routine completion.
     */
    fun playFinishSound() {
        toneGen.startTone(ToneGenerator.TONE_PROP_ACK, 200)
    }

    /**
     * Subtle click for reset.
     */
    fun playResetSound() {
        toneGen.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE, 50)
    }
}
