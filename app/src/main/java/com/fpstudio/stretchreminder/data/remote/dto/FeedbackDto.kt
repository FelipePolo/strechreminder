package com.fpstudio.stretchreminder.data.remote.dto

import com.google.gson.annotations.SerializedName

data class FeedbackRequest(
    @SerializedName("subject")
    val subject: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("platform")
    val platform: String,
    @SerializedName("user_type")
    val userType: String,
    @SerializedName("country")
    val country: String
)

data class FeedbackResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String
)
