package com.fpstudio.stretchreminder.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.fpstudio.stretchreminder.data.local.Converters

@Entity(tableName = "user")
@TypeConverters(Converters::class)
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String = "",
    val gender: String = "",
    val age: String = "",
    val mainPosture: String = "",
    val workDays: List<String> = emptyList(),
    val startTime: String = "",
    val endTime: String = "",
    val notificationPermission: Boolean = false
)
