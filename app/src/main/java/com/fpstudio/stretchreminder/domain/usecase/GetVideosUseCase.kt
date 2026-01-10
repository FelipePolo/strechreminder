package com.fpstudio.stretchreminder.domain.usecase

import com.fpstudio.stretchreminder.data.model.VideosWithRecommended
import com.fpstudio.stretchreminder.domain.repository.VideoRepository

class GetVideosUseCase(
    private val repository: VideoRepository
) {
    suspend operator fun invoke(language: String = "en"): Result<VideosWithRecommended> {
        return repository.getVideos(language)
    }
}
