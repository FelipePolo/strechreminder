package com.fpstudio.stretchreminder.ui

import kotlinx.serialization.Serializable

@Serializable
object Intro

@Serializable
object Form

@Serializable
object ThreeYes

@Serializable
object Congratulation

@Serializable
object Tutorial

@Serializable
object Home

@Serializable
object RoutineSelection

@Serializable
data class Exercise(val videoUrls: List<String>)

@Serializable
object Settings

@Serializable
object Premium

@Serializable
object PremiumSuccess
