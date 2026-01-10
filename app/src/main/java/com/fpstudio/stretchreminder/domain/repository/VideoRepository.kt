package com.fpstudio.stretchreminder.domain.repository

import com.fpstudio.stretchreminder.data.model.VideosWithRecommended

interface VideoRepository {
    suspend fun getVideos(language: String): Result<VideosWithRecommended>
}
