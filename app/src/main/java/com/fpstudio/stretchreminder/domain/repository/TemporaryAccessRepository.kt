package com.fpstudio.stretchreminder.domain.repository

import kotlinx.coroutines.flow.StateFlow

interface TemporaryAccessRepository {
    val temporarilyUnlockedVideoIds: StateFlow<Set<String>>
    val temporarilyUnlockedRoutineIds: StateFlow<Set<Int>>

    fun unlockVideo(videoId: String)
    fun unlockRoutine(routineId: Int)
    fun clearAll()
}
