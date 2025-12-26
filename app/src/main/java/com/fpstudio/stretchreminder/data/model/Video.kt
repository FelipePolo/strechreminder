package com.fpstudio.stretchreminder.data.model

data class Video(
    val id: String,
    val name: String,
    val thumbnailUrl: String,
    val duration: Int,
    val videoUrl: String,
    val title: String,
    val bodyPart: BodyPartID,
    val badge: Badge,
    val isNew: Boolean = false,
    val isSelected: Boolean = false
)
