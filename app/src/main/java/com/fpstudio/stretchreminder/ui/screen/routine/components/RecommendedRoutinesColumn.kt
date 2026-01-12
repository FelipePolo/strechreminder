package com.fpstudio.stretchreminder.ui.screen.routine.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fpstudio.stretchreminder.data.model.RecommendedRoutine

@Composable
fun RecommendedRoutinesColumn(
    routines: List<RecommendedRoutine>,
    selectedRoutineId: Int?,
    userIsPremium: Boolean,
    temporarilyUnlockedRoutineIds: Set<Int> = emptySet(),
    bestMatchRoutineId: Int? = null,
    onRoutineClick: (RecommendedRoutine) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(routines) { routine ->
            RecommendedRoutineCard(
                routine = routine,
                isSelected = routine.id == selectedRoutineId,
                userIsPremium = userIsPremium,
                isTemporarilyUnlocked = routine.id in temporarilyUnlockedRoutineIds,
                isBestMatch = routine.id == bestMatchRoutineId,
                onClick = { onRoutineClick(routine) }
            )
        }
    }
}
