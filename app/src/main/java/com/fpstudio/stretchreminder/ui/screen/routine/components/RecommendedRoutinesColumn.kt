package com.fpstudio.stretchreminder.ui.screen.routine.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fpstudio.stretchreminder.data.model.RecommendedRoutine

@Composable
fun RecommendedRoutinesColumn(
    routines: List<RecommendedRoutine>,
    savedRoutines: List<com.fpstudio.stretchreminder.data.model.Routine> = emptyList(),
    selectedRoutineId: Int?,
    selectedCustomRoutineId: Long?,
    userIsPremium: Boolean,
    temporarilyUnlockedRoutineIds: Set<Int> = emptySet(),
    bestMatchRoutineId: Int? = null,
    onRoutineClick: (RecommendedRoutine) -> Unit,
    onNavigateToCreate: () -> Unit,
    onNavigateToPremium: () -> Unit,
    onSavedRoutineClick: (Long) -> Unit,
    onEditRoutine: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            MyRoutinesSection(
                savedRoutines = savedRoutines,
                userIsPremium = userIsPremium,
                selectedRoutineId = selectedCustomRoutineId,
                onNavigateToCreate = onNavigateToCreate,
                onNavigateToPremium = onNavigateToPremium,
                onRoutineClick = onSavedRoutineClick,
                onEditRoutine = onEditRoutine
            )
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recommended",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F2937) // Dark Slate/Black
                )
                Text(
                    text = "CREATED FOR YOU",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF9CA3AF), // Gray-400
                    letterSpacing = 1.sp
                )
            }
        }

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
