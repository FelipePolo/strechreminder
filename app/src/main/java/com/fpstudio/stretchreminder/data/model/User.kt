package com.fpstudio.stretchreminder.data.model

data class User(
    val name: String = "",
    val lastFormPage: Int = 0,
    val gender: Int = 0,
    val ageRange: Int = 0,
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
    val title: Int,
    val description: Int
)

data class BodyPart(
    val id: BodyPartID,
    val name: Int
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

    val displayNameRes: Int
        get() = when (this) {
            NECK -> com.fpstudio.stretchreminder.R.string.body_part_neck
            SHOULDERS -> com.fpstudio.stretchreminder.R.string.body_part_shoulders
            UPPER_BACK -> com.fpstudio.stretchreminder.R.string.body_part_upper_back
            LOWER_BACK -> com.fpstudio.stretchreminder.R.string.body_part_lower_back
            HANDS -> com.fpstudio.stretchreminder.R.string.body_part_hands
            HIP -> com.fpstudio.stretchreminder.R.string.body_part_hips
            LEGS -> com.fpstudio.stretchreminder.R.string.body_part_legs
            All -> com.fpstudio.stretchreminder.R.string.app_name // Fallback or need "All" string if used in form. But FormData uses enum instances. "All" not used in FormData options.
        }
}
