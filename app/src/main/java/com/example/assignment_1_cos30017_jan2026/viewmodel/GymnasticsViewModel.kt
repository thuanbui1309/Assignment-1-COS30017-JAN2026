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

    // Feedback events for UI
    sealed class FeedbackEvent {
        data class PerformSuccess(val points: Int, val zone: Zone) : FeedbackEvent()
        data class Deduction(val points: Int) : FeedbackEvent()
        data class RoutineComplete(val finalScore: Int) : FeedbackEvent()
        data class RoutineEndedEarly(val finalScore: Int) : FeedbackEvent()
        object Reset : FeedbackEvent()
    }

    private val _event = MutableLiveData<FeedbackEvent?>()
    val event: LiveData<FeedbackEvent?> = _event

    fun eventHandled() {
        _event.value = null
    }

    // Add points by performing the next element
    fun perform() {
        val currentState = _state.value ?: return

        // Return if got deducted - routine ended early
        if (currentState.hasDeducted) {
            Log.d(TAG, "perform() blocked - routine ended due to deduction")
            return
        }

        // Return if routine is complete
        if (currentState.isFinished) {
            Log.d(TAG, "perform() blocked - routine already finished")
            return
        }

        // Calculate the element being performed (next element)
        val elementBeingPerformed = currentState.currentElement + 1
        
        // Get zone for the element being performed
        val zone = Zone.fromElement(elementBeingPerformed)
        val pointsToAdd = zone.pointValue
        val newScore = minOf(currentState.score + pointsToAdd, MAX_SCORE)
        
        // Check if this completes the routine
        val isNowFinished = elementBeingPerformed >= MAX_ELEMENTS

        // Update state
        _state.value = currentState.copy(
            score = newScore,
            currentElement = elementBeingPerformed,
            isFinished = isNowFinished
        )

        Log.d(TAG, "perform(): element=$elementBeingPerformed, zone=${zone.name}, +$pointsToAdd pts, total=$newScore, finished=$isNowFinished")

        // Emit feedback event
        _event.value = if (isNowFinished) {
            FeedbackEvent.RoutineComplete(newScore)
        } else {
            FeedbackEvent.PerformSuccess(pointsToAdd, zone)
        }
    }

    // Deduct points
    fun deduct() {
        val currentState = _state.value ?: return

        // Return if in initial state
        if (currentState.currentElement < 1) {
            Log.d(TAG, "deduct() blocked - complete at least one element first")
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

        // Emit feedback event
        _event.value = FeedbackEvent.RoutineEndedEarly(newScore)
    }

    // Reset state
    fun reset() {
        Log.d(TAG, "reset(): Reset to initial state")
        _state.value = GymnasticsState()
        _event.value = FeedbackEvent.Reset
    }

    // Get zone based on current element
    fun getCurrentZone(): Zone {
        val element = _state.value?.currentElement ?: 0
        return Zone.fromElement(if (element == 0) 1 else element)
    }
}
