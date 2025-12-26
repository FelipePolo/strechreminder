package com.fpstudio.stretchreminder.domain.repository

import com.fpstudio.stretchreminder.data.model.Video

interface VideoRepository {
    suspend fun getVideos(language: String): Result<List<Video>>
}
