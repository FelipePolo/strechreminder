package com.fpstudio.stretchreminder.ui.screen.routine.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fpstudio.stretchreminder.data.model.BodyPartID
import com.fpstudio.stretchreminder.ui.theme.TurquoiseAccent

sealed class VideoFilter {
    object All : VideoFilter()
    object Recommended : VideoFilter()
    data class ByBodyPart(val bodyPart: BodyPartID) : VideoFilter()
}

@Composable
fun FilterChipsRow(
    selectedFilter: VideoFilter,
    onFilterSelected: (VideoFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
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
        items(BodyPartID.values().filter { it != BodyPartID.All }) { bodyPart ->
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
