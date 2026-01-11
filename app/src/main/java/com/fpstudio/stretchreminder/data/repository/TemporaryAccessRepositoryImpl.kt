package com.fpstudio.stretchreminder.data.repository

import com.fpstudio.stretchreminder.domain.repository.TemporaryAccessRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TemporaryAccessRepositoryImpl : TemporaryAccessRepository {
    private val _temporarilyUnlockedVideoIds = MutableStateFlow<Set<String>>(emptySet())
    override val temporarilyUnlockedVideoIds: StateFlow<Set<String>> = _temporarilyUnlockedVideoIds.asStateFlow()

    private val _temporarilyUnlockedRoutineIds = MutableStateFlow<Set<Int>>(emptySet())
    override val temporarilyUnlockedRoutineIds: StateFlow<Set<Int>> = _temporarilyUnlockedRoutineIds.asStateFlow()

    override fun unlockVideo(videoId: String) {
        _temporarilyUnlockedVideoIds.update { it + videoId }
    }

    override fun unlockRoutine(routineId: Int) {
        _temporarilyUnlockedRoutineIds.update { it + routineId }
    }

    override fun clearAll() {
        _temporarilyUnlockedVideoIds.value = emptySet()
        _temporarilyUnlockedRoutineIds.value = emptySet()
    }
}
