package com.fpstudio.stretchreminder.data.remote

import com.fpstudio.stretchreminder.data.remote.dto.BodyPartsResponse
import com.fpstudio.stretchreminder.data.remote.dto.VideoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoApiService {
    
    @GET("V2/index.php")
    suspend fun getVideos(
        @Query("lang") language: String,
        @Query("token") token: String
    ): VideoResponse
    
    @GET("V2/get_bodyparts.php")
    suspend fun getBodyParts(
        @Query("lang") language: String = "en"
    ): BodyPartsResponse
}
