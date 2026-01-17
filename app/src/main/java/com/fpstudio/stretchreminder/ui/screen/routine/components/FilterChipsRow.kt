package com.fpstudio.stretchreminder.ui.screen.routine.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.fpstudio.stretchreminder.data.model.BodyPartID
import com.fpstudio.stretchreminder.ui.theme.TurquoiseAccent
import kotlinx.coroutines.launch

sealed class VideoFilter {
    object All : VideoFilter()
    object Recommended : VideoFilter()
    data class ByBodyPart(val bodyPart: BodyPartID) : VideoFilter()
}

@Composable
fun FilterChipsRow(
    selectedFilter: VideoFilter,
    onFilterSelected: (VideoFilter) -> Unit,
    availableBodyParts: List<BodyPartID> = BodyPartID.values().filter { it != BodyPartID.All }.toList(),
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    
    // Calculate total items and selected index
    val totalItems = 2 + availableBodyParts.size
    val selectedIndex = when (selectedFilter) {
        is VideoFilter.Recommended -> 0
        is VideoFilter.All -> 1
        is VideoFilter.ByBodyPart -> {
            val bodyPartIndex = availableBodyParts.indexOf(selectedFilter.bodyPart)
            if (bodyPartIndex >= 0) 2 + bodyPartIndex else -1
        }
    }
    
    // Auto-scroll to center selected filter (except first 2 and last 2)
    LaunchedEffect(selectedFilter) {
        if (selectedIndex >= 2 && selectedIndex < totalItems - 2) {
            coroutineScope.launch {
                // Scroll to center the selected item
                // Using scrollOffset to position the item in the middle of the viewport
                listState.animateScrollToItem(
                    index = selectedIndex,
                    scrollOffset = -(screenWidthDp.value.toInt() / 2 - 80) // Approximate centering
                )
            }
        } else if (selectedIndex == 1) {
            // For "All" filter, scroll with offset to show part of "Recommended"
            coroutineScope.launch {
                listState.animateScrollToItem(
                    index = selectedIndex,
                    scrollOffset = -60 // Negative offset to show previous item partially
                )
            }
        } else if (selectedIndex >= 0) {
            // For first (Recommended) or last items, just ensure they're visible
            coroutineScope.launch {
                listState.animateScrollToItem(index = selectedIndex)
            }
        }
    }
    
    LazyRow(
        state = listState,
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Recommended Chip
        item {
            FilterChip(
                selected = selectedFilter == VideoFilter.Recommended,
                onClick = { onFilterSelected(VideoFilter.Recommended) },
                label = {
                    Text(
                        text = "Recommended",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = TurquoiseAccent,
                    selectedLabelColor = Color.White,
                    containerColor = Color.White,
                    labelColor = Color.Black
                ),
                shape = CircleShape,
                border = null
            )
        }
        
        // All Chip
        item {
            FilterChip(
                selected = selectedFilter == VideoFilter.All,
                onClick = { onFilterSelected(VideoFilter.All) },
                label = {
                    Text(
                        text = "All",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = TurquoiseAccent,
                    selectedLabelColor = Color.White,
                    containerColor = Color.White,
                    labelColor = Color.Black
                ),
                shape = CircleShape,
                border = null
            )
        }
        
        // Body Part Chips
        items(availableBodyParts) { bodyPart ->
            FilterChip(
                selected = selectedFilter is VideoFilter.ByBodyPart && 
                          selectedFilter.bodyPart == bodyPart,
                onClick = { onFilterSelected(VideoFilter.ByBodyPart(bodyPart)) },
                label = {
                    Text(
                        text = bodyPart.displayName,
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = TurquoiseAccent,
                    selectedLabelColor = Color.White,
                    containerColor = Color.White,
                    labelColor = Color.Black
                ),
                shape = CircleShape,
                border = null
            )
        }
    }
}
