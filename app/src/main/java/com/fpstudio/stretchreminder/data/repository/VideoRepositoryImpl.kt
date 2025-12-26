package com.fpstudio.stretchreminder.data.repository

import com.fpstudio.stretchreminder.data.mapper.VideoMapper.toDomain
import com.fpstudio.stretchreminder.data.model.Video
import com.fpstudio.stretchreminder.data.remote.VideoApiService
import com.fpstudio.stretchreminder.data.util.TokenGenerator
import com.fpstudio.stretchreminder.domain.repository.VideoRepository

class VideoRepositoryImpl(
    private val apiService: VideoApiService
) : VideoRepository {
    
    override suspend fun getVideos(language: String): Result<List<Video>> {
        return try {
            val token = TokenGenerator.generateHmacToken(message = language)
            val response = apiService.getVideos(language, token)
            
            if (response.success) {
                val videos = response.videos.toDomain()
                Result.success(videos)
            } else {
                Result.failure(Exception("API returned success=false"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
