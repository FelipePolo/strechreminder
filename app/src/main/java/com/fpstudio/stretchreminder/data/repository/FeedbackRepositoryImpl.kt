package com.fpstudio.stretchreminder.data.repository

import com.fpstudio.stretchreminder.data.remote.VideoApiService
import com.fpstudio.stretchreminder.data.remote.dto.FeedbackRequest
import com.fpstudio.stretchreminder.domain.repository.FeedbackRepository

class FeedbackRepositoryImpl(
    private val apiService: VideoApiService
) : FeedbackRepository {
    override suspend fun submitFeedback(
        subject: String,
        message: String,
        platform: String,
        userType: String,
        country: String
    ): Result<String> {
        return try {
            val request = FeedbackRequest(
                subject = subject,
                message = message,
                platform = platform,
                userType = userType,
                country = country
            )
            val response = apiService.submitFeedback(request)
            if (response.success) {
                Result.success(response.message)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
