package com.fpstudio.stretchreminder.data.remote.dto

import com.google.gson.annotations.SerializedName

data class VideoDto(
    @SerializedName("id")
    val id: String,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("thumbnail")
    val thumbnail: String,
    
    @SerializedName("duration")
    val duration: Int,
    
    @SerializedName("url")
    val url: String,
    
    @SerializedName("title")
    val title: String,
    
    @SerializedName("bodypart")
    val bodypart: String,
    
    @SerializedName("badge")
    val badge: BadgeDto?
)
