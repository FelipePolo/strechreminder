package com.fpstudio.stretchreminder.data.remote.dto

import com.google.gson.annotations.SerializedName

data class BodyPartDto(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("name")
    val name: String
)
