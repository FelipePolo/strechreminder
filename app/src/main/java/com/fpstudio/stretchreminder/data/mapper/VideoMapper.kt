package com.fpstudio.stretchreminder.data.mapper

import androidx.compose.ui.graphics.Color
import com.fpstudio.stretchreminder.data.model.Badge
import com.fpstudio.stretchreminder.data.model.BodyPartID
import com.fpstudio.stretchreminder.data.model.UserType
import com.fpstudio.stretchreminder.data.model.Video
import com.fpstudio.stretchreminder.data.model.VideoVisibility
import com.fpstudio.stretchreminder.data.remote.dto.VideoDto

object VideoMapper {

    private const val BASE_URL = "https://stretchreminder.net"

    fun VideoDto.toDomain(): Video {
        return Video(
            id = id,
            name = name,
            thumbnailUrl = "$BASE_URL$thumbnail",
            duration = duration,
            videoUrl = "$BASE_URL$url",
            title = title,
            bodyParts = bodyParts.map { it.name.toBodyPartID() },
            badge = badge?.let {
                Badge(
                    name = it.name,
                    backgroundColor = parseColor(it.backgroundColor)
                )
            },
            visibility = VideoVisibility.fromString(visibility),
            userType = UserType.fromString(userType),
            isSelected = false
        )
    }

    fun List<VideoDto>.toDomain(): List<Video> {
        return map { it.toDomain() }
    }

    private fun String.toBodyPartID(): BodyPartID {
        return when (this.lowercase().trim()) {
            "neck" -> BodyPartID.NECK
            "shoulders", "shoulder" -> BodyPartID.SHOULDERS
            "arms", "arm" -> BodyPartID.ARMS
            "trapezoids", "trapezoid", "traps" -> BodyPartID.TRAPEZOIDS
            "lower_back", "lowerback", "lower back" -> BodyPartID.LOWER_BACK
            "hands", "hand" -> BodyPartID.HANDS
            "hip", "hips" -> BodyPartID.HIP
            "legs", "leg" -> BodyPartID.LEGS
            else -> BodyPartID.All
        }
    }

    private fun parseColor(colorString: String): Color {
        return try {
            val color = android.graphics.Color.parseColor(colorString)
            Color(color)
        } catch (e: Exception) {
            Color.Gray // Default color if parsing fails
        }
    }
}
