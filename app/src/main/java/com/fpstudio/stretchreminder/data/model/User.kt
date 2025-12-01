package com.fpstudio.stretchreminder.data.model

data class User(
    val name: String = "",
    val lastFormPage: Int = 0,
    val gender: String = "",
    val age: String = "",
    val mainPosture: Int = 0,
    val workDays: List<String> = emptyList(),
    val achievement: List<String> = emptyList(),
    val startTime: Long = 0L,
    val endTime: Long = 0L,
    val bodyParts: List<String> = emptyList(),
    val frequency: Int = 0,
    val tutorialDone: Boolean = false,
    val notificationPermission: Boolean = false
)
