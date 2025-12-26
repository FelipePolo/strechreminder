package com.fpstudio.stretchreminder.data.remote.dto

import com.google.gson.annotations.SerializedName

data class VideoResponse(
    @SerializedName("success")
    val success: Boolean,
    
    @SerializedName("language")
    val language: String,
    
    @SerializedName("count")
    val count: Int,
    
    @SerializedName("videos")
    val videos: List<VideoDto>
)
