package com.fpstudio.stretchreminder.data.model

data class VideosWithRecommended(
    val videos: List<Video>,
    val recommendedRoutines: List<RecommendedRoutine>
)
