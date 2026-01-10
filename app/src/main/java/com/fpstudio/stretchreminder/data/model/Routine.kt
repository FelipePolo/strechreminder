package com.fpstudio.stretchreminder.data.model

data class Routine(
    val id: Long = System.currentTimeMillis(),
    val name: String,
    val icon: RoutineIcon,
    val color: RoutineColor,
    val videoIds: List<String>, // Store video IDs in order
    val totalDuration: Int, // Total duration in seconds
    val createdAt: Long = System.currentTimeMillis()
)

enum class RoutineIcon(val iconRes: Int) {
    STRETCH(com.fpstudio.stretchreminder.R.drawable.routine_icon_1),
    MEDITATION(com.fpstudio.stretchreminder.R.drawable.routine_icon_2),
    RUNNING(com.fpstudio.stretchreminder.R.drawable.routine_icon_3),
    MOON(com.fpstudio.stretchreminder.R.drawable.routine_icon_4),
    DUMBBELL(com.fpstudio.stretchreminder.R.drawable.routine_icon_5);
    
    val displayName: String
        get() = when (this) {
            STRETCH -> "Stretch"
            MEDITATION -> "Meditation"
            RUNNING -> "Running"
            MOON -> "Night"
            DUMBBELL -> "Strength"
        }
}

enum class RoutineColor(val hex: String) {
    TURQUOISE("#1ABC9C"),
    BLUE("#3498DB"),
    PURPLE("#9B59B6"),
    PINK("#E91E63"),
    ORANGE("#FF9800"),
    YELLOW("#FCBF23")
}
