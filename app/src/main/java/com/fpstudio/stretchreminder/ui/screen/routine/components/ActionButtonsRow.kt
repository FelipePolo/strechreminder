package com.fpstudio.stretchreminder.ui.screen.routine.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ActionButtonsRow(
    selectedCount: Int,
    totalDurationSeconds: Int,
    hasSavedRoutines: Boolean,
    onSaveRoutine: () -> Unit,
    onMyRoutines: () -> Unit,
    onStart: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        when {
            // Case 1: No videos selected + has saved routines → Show "My Routines" + disabled "Start"
            selectedCount == 0 && hasSavedRoutines -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SaveRoutineButton(
                        onClick = onMyRoutines, // This will show MyRoutinesBottomSheet
                        text = "My Routines",
                        modifier = Modifier.weight(1f)
                    )
                    ContinueButton(
                        enabled = false,
                        selectedCount = selectedCount,
                        totalDurationSeconds = totalDurationSeconds,
                        onClick = onStart,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            
            // Case 2: 2+ videos selected → Show "Save Routine" + enabled "Start"
            selectedCount >= 2 -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SaveRoutineButton(
                        onClick = onSaveRoutine,
                        text = "Save Routine",
                        modifier = Modifier.weight(1f)
                    )
                    ContinueButton(
                        enabled = true,
                        selectedCount = selectedCount,
                        totalDurationSeconds = totalDurationSeconds,
                        onClick = onStart,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            
            // Case 3: 1 video selected OR (no videos + no saved routines) → Show only "Start" button
            else -> {
                ContinueButton(
                    enabled = selectedCount > 0,
                    selectedCount = selectedCount,
                    totalDurationSeconds = totalDurationSeconds,
                    onClick = onStart,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
