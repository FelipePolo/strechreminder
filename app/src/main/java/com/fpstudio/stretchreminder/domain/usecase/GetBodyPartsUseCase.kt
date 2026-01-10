package com.fpstudio.stretchreminder.domain.usecase

import com.fpstudio.stretchreminder.data.model.BodyPartID
import com.fpstudio.stretchreminder.data.repository.BodyPartRepository

/**
 * Use case to get available body parts for filtering.
 * Always returns a list (never fails) by using fallback data when API is unavailable.
 */
class GetBodyPartsUseCase(
    private val bodyPartRepository: BodyPartRepository
) {
    
    suspend operator fun invoke(language: String = "en"): List<BodyPartID> {
        return bodyPartRepository.getBodyParts(language).getOrElse {
            // This should never happen since repository always returns success,
            // but keeping as a safety net
            emptyList()
        }
    }
}
