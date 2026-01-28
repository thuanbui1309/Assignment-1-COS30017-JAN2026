package com.example.assignment_1_cos30017_jan2026.model

// Data class representing the state of a gymnastics routine
data class GymnasticsState(
    val score: Int = 0,
    val currentElement: Int = 1,
    val hasDeducted: Boolean = false,
    val isFinished: Boolean = false
)
