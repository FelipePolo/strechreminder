package com.fpstudio.stretchreminder.domain.repository

interface FeedbackRepository {
    suspend fun submitFeedback(
        subject: String,
        message: String,
        platform: String,
        userType: String,
        country: String
    ): Result<String>
}
