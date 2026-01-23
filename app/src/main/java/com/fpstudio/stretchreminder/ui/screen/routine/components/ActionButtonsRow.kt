package com.fpstudio.stretchreminder.ui.screen.routine.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fpstudio.stretchreminder.R
import androidx.compose.ui.res.stringResource

@Composable
fun ActionButtonsRow(
    selectedCount: Int,
    totalDurationSeconds: Int,
    userIsPremium: Boolean,
    hasCustomRoutineSelected: Boolean = false,
    onSaveRoutine: () -> Unit,
    onStart: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        when {
            // Case 1: Custom routine selected → Only show "Start" button (no Save)
            hasCustomRoutineSelected && selectedCount > 0 -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ContinueButton(
                        enabled = true,
                        selectedCount = selectedCount,
                        totalDurationSeconds = totalDurationSeconds,
                        onClick = onStart,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            
            // Case 2: 2+ videos selected → Show "Save Routine" + enabled "Start"
            selectedCount >= 2 -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SaveRoutineButton(
                        onClick = onSaveRoutine,
                        text = stringResource(R.string.action_save),
                        modifier = Modifier.weight(0.4f),
                        leadingIcon = if (userIsPremium) {
                            {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        } else null,
                        trailingIcon = if (!userIsPremium) {
                            {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = stringResource(R.string.content_desc_locked),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        } else null
                    )
                    ContinueButton(
                        enabled = true,
                        selectedCount = selectedCount,
                        totalDurationSeconds = totalDurationSeconds,
                        onClick = onStart,
                        modifier = Modifier.weight(0.6f)
                    )
                }
            }
            
            else -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ContinueButton(
                        enabled = selectedCount > 0,
                        selectedCount = selectedCount,
                        totalDurationSeconds = totalDurationSeconds,
                        onClick = onStart,
                        modifier = Modifier.weight(0.8f)
                    )
                }
            }
        }
    }
}
