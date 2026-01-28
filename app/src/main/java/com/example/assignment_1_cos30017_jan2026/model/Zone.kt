package com.example.assignment_1_cos30017_jan2026.model

// Enumeration representing different zones with associated point values
enum class Zone(val pointValue: Int) {
    BASIC(1),
    INTERMEDIATE(2),
    ADVANCED(3);

    companion object {
        // Function to get Zone by its point value
        fun fromElement(element: Int): Zone {
            return when (element) {
                in 1..3 -> BASIC
                in 4..7 -> INTERMEDIATE
                in 8..10 -> ADVANCED
                else -> BASIC
            }
        }
    }
}