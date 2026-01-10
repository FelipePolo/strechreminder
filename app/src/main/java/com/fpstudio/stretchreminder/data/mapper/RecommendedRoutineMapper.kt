package com.fpstudio.stretchreminder.data.mapper

import com.fpstudio.stretchreminder.data.model.BodyPartID
import com.fpstudio.stretchreminder.data.model.RecommendedRoutine
import com.fpstudio.stretchreminder.data.model.RoutineTag
import com.fpstudio.stretchreminder.data.model.UserType
import com.fpstudio.stretchreminder.data.model.VideoVisibility
import com.fpstudio.stretchreminder.data.remote.dto.RecommendedRoutineDto
import com.fpstudio.stretchreminder.data.mapper.VideoMapper.toDomain

object RecommendedRoutineMapper {
    
    fun List<RecommendedRoutineDto>.toDomain(): List<RecommendedRoutine> {
        return map { it.toDomain() }
    }
    
    fun RecommendedRoutineDto.toDomain(): RecommendedRoutine {
        return RecommendedRoutine(
            id = id,
            name = name,
            userType = UserType.fromString(userType),
            visibility = VideoVisibility.fromString(visibility),
            thumbnail = thumbnail,
            quantity = quantity,
            duration = duration,
            tags = tags.map { RoutineTag(id = it.id, name = it.name) },
            bodyparts = bodyparts.map { bodyPartDto ->
                bodyPartDto.name.lowercase().toBodyPartID()
            },
            videos = videos.toDomain()
        )
    }
    
    private fun String.toBodyPartID(): BodyPartID {
        return when (this.lowercase()) {
            "neck" -> BodyPartID.NECK
            "shoulders", "shoulder" -> BodyPartID.SHOULDERS
            "upperback", "upper_back", "upper back" -> BodyPartID.UPPER_BACK
            "lower_back", "lowerback", "lower back" -> BodyPartID.LOWER_BACK
            "hands", "hand" -> BodyPartID.HANDS
            "hip", "hips" -> BodyPartID.HIP
            "legs", "leg" -> BodyPartID.LEGS
            else -> BodyPartID.All
        }
    }
}
