package com.fpstudio.stretchreminder.data.model

data class User(
    val name: String = "",
    val lastFormPage: Int = 0,
    val gender: String = "",
    val ageRange: String = "",
    val mainPosture: Int = 0,
    val workDays: List<String> = emptyList(),
    val achievement: List<UserAchievement> = emptyList(),
    val startTime: Long = 0L,
    val endTime: Long = 0L,
    val bodyParts: List<BodyPartID> = emptyList(),
    val frequency: Int = 0,
    val tutorialDone: Boolean = false,
    val notificationPermission: Boolean = false
)

data class UserAchievement(
    val iconStr: String,
    val title: String,
    val description: String
)

data class BodyPart(
    val id: BodyPartID,
    val name: String
)

enum class BodyPartID {
    NECK,
    SHOULDERS,
    UPPER_BACK,
    LOWER_BACK,
    HANDS,
    HIP,
    LEGS,
    All;
    
    val displayName: String
        get() = when (this) {
            NECK -> "Neck"
            SHOULDERS -> "Shoulders"
            UPPER_BACK -> "Upper back"
            LOWER_BACK -> "Lower Back"
            HANDS -> "Hands"
            HIP -> "Hips"
            LEGS -> "Legs"
            All -> "All"
        }
}
