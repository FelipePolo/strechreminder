package com.fpstudio.stretchreminder.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RecommendedRoutineDto(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("user_type")
    val userType: String,
    
    @SerializedName("visibility")
    val visibility: String,
    
    @SerializedName("thumbnail")
    val thumbnail: String,
    
    @SerializedName("quantity")
    val quantity: Int,
    
    @SerializedName("duration")
    val duration: Int,
    
    @SerializedName("tags")
    val tags: List<RoutineTagDto>,
    
    @SerializedName("bodyparts")
    val bodyparts: List<RoutineBodyPartDto>,
    
    @SerializedName("videos")
    val videos: List<VideoDto>
)

data class RoutineTagDto(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("name")
    val name: String
)

data class RoutineBodyPartDto(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("name")
    val name: String
)
