package com.fpstudio.stretchreminder.data.remote.dto

import com.google.gson.annotations.SerializedName

data class BodyPartDto(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("key")
    val key: String,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("description")
    val description: String
)
