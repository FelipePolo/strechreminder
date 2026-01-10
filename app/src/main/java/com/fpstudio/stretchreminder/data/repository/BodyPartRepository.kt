package com.fpstudio.stretchreminder.data.repository

import com.fpstudio.stretchreminder.data.model.BodyPartID
import com.fpstudio.stretchreminder.data.remote.VideoApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BodyPartRepository(
    private val apiService: VideoApiService
) {
    
    // Fallback body parts list matching the API default
    private val fallbackBodyParts = listOf(
        BodyPartID.NECK,
        BodyPartID.HANDS,
        BodyPartID.SHOULDERS,
        BodyPartID.LEGS,
        BodyPartID.UPPER_BACK, // upperback
        BodyPartID.LOWER_BACK,
        BodyPartID.HIP
    )
    
    /**
     * Fetches body parts from the API. If the request fails or the user is offline,
     * returns the fallback list.
     */
    suspend fun getBodyParts(language: String = "en"): Result<List<BodyPartID>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getBodyParts(language)
            
            if (response.success && response.bodyparts.isNotEmpty()) {
                val bodyParts = response.bodyparts.mapNotNull { dto ->
                    mapKeyToBodyPartID(dto.key)
                }
                Result.success(bodyParts.ifEmpty { fallbackBodyParts })
            } else {
                Result.success(fallbackBodyParts)
            }
        } catch (e: Exception) {
            // On any error (network, parsing, etc.), return fallback
            Result.success(fallbackBodyParts)
        }
    }
    
    /**
     * Maps API keys to BodyPartID enum values
     */
    private fun mapKeyToBodyPartID(key: String): BodyPartID? {
        return when (key.lowercase()) {
            "neck" -> BodyPartID.NECK
            "hands" -> BodyPartID.HANDS
            "shoulder", "shoulders" -> BodyPartID.SHOULDERS
            "legs" -> BodyPartID.LEGS
            "upperback" -> BodyPartID.UPPER_BACK
            "lowerback" -> BodyPartID.LOWER_BACK
            "hips", "hip" -> BodyPartID.HIP
            else -> null // Ignore unknown body parts
        }
    }
}
