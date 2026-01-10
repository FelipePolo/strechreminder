package com.fpstudio.stretchreminder.data.model

data class RecommendedRoutine(
    val id: Int,
    val name: String,
    val userType: UserType,
    val visibility: VideoVisibility,
    val thumbnail: String,
    val quantity: Int,
    val duration: Int,
    val tags: List<RoutineTag>,
    val bodyparts: List<BodyPartID>,
    val videos: List<Video>
)

data class RoutineTag(
    val id: Int,
    val name: String
)
