package com.example.assignment_1_cos30017_jan2026.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Log
import com.example.assignment_1_cos30017_jan2026.model.GymnasticsState
import com.example.assignment_1_cos30017_jan2026.model.Zone

class GymnasticsViewModel : ViewModel() {

    companion object {
        private const val TAG = "GymnasticsViewModel"
        private const val MAX_ELEMENTS = 10
        private const val DEDUCTION_POINTS = 2
        private const val MAX_SCORE = 20
        private const val MIN_SCORE = 0
    }

    // Gymnastics routine state
    private val _state = MutableLiveData(GymnasticsState())
    val state: LiveData<GymnasticsState> = _state

    // Add points
    fun perform() {
        val currentState = _state.value ?: return

        // Return if got deducted/routine ended
        if (currentState.hasDeducted) {
            Log.d(TAG, "perform() blocked - routine already ended due to deduction")
            return
        }

        if (currentState.isFinished) {
            Log.d(TAG, "perform() blocked - routine already finished")
            return
        }

        // Get current state
        val zone = getCurrentZone()
        val pointsToAdd = zone.pointValue
        val newScore = minOf(currentState.score + pointsToAdd, MAX_SCORE)
        val newElement = currentState.currentElement + 1
        val isNowFinished = currentState.currentElement >= MAX_ELEMENTS

        // Update state
        _state.value = currentState.copy(
            score = newScore,
            currentElement = if (isNowFinished) MAX_ELEMENTS else newElement,
            isFinished = isNowFinished
        )

        Log.d(TAG, "perform(): +$pointsToAdd pts, score=$newScore, element=$newElement")
    }

    // Deduct points
    fun deduct() {
        val currentState = _state.value ?: return

        // Return if in initial state
        if (currentState.currentElement <= 1) {
            Log.d(TAG, "deduct() blocked - complete first element first")
            return
        }

        // Return if got deducted/routine ended
        if (currentState.hasDeducted) {
            Log.d(TAG, "deduct() blocked - routine already ended due to deduction")
            return
        }

        if (currentState.isFinished) {
            Log.d(TAG, "deduct() blocked - routine already finished")
            return
        }

        // Deduct points and end routine
        val newScore = maxOf(currentState.score - DEDUCTION_POINTS, MIN_SCORE)

        _state.value = currentState.copy(
            score = newScore,
            hasDeducted = true
        )

        Log.d(TAG, "deduct(): -$DEDUCTION_POINTS pts, score=$newScore")
    }

    // Reset state
    fun reset() {
        Log.d(TAG, "reset(): Reset to initial state")
        _state.value = GymnasticsState()
    }

    // Get zone based on current element
    fun getCurrentZone(): Zone {
        return Zone.fromElement(_state.value?.currentElement ?: 1)
    }
}
