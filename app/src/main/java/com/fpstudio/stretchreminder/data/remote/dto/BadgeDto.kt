package com.fpstudio.stretchreminder.data.remote.dto

import com.google.gson.annotations.SerializedName

data class BadgeDto(
    @SerializedName("name")
    val name: String,
    
    @SerializedName("backgroundColor")
    val backgroundColor: String
)
