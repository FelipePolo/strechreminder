package com.fpstudio.stretchreminder.data.model

data class Video(
    val id: String,
    val name: String,
    val thumbnailUrl: String,
    val duration: Int,
    val videoUrl: String,
    val title: String,
    val bodyParts: List<BodyPartID>,
    val badge: Badge?,
    val visibility: VideoVisibility,
    val userType: UserType,
    val isSelected: Boolean = false
)

enum class VideoVisibility {
    PUBLIC,
    PRIVATE;
    
    companion object {
        fun fromString(value: String): VideoVisibility {
            return when (value.lowercase()) {
                "public" -> PUBLIC
                "private" -> PRIVATE
                else -> PUBLIC
            }
        }
    }
}

enum class UserType {
    FREE,
    PREMIUM;
    
    companion object {
        fun fromString(value: String): UserType {
            return when (value.lowercase()) {
                "free" -> FREE
                "premium" -> PREMIUM
                else -> FREE
            }
        }
    }
}
