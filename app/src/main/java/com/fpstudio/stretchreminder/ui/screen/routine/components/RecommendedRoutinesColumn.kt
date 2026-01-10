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
    onRoutineClick: (RecommendedRoutine) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(routines) { routine ->
            RecommendedRoutineCard(
                routine = routine,
                isSelected = routine.id == selectedRoutineId,
                onClick = { onRoutineClick(routine) }
            )
        }
    }
}
