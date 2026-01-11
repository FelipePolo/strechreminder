package com.fpstudio.stretchreminder.domain.usecase

import com.fpstudio.stretchreminder.domain.repository.FeedbackRepository

class SubmitFeedbackUseCase(
    private val feedbackRepository: FeedbackRepository
) {
    suspend operator fun invoke(
        subject: String,
        message: String,
        platform: String,
        userType: String,
        country: String
    ): Result<String> {
        return feedbackRepository.submitFeedback(
            subject = subject,
            message = message,
            platform = platform,
            userType = userType,
            country = country
        )
    }
}
