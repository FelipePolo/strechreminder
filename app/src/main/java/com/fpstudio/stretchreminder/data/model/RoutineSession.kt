package com.fpstudio.stretchreminder.data.model

data class RoutineSession(
    val date: String, // ISO format YYYY-MM-DD
    val durationSeconds: Long,
    val completedAt: Long // Timestamp in milliseconds
)
