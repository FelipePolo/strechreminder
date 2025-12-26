package com.fpstudio.stretchreminder.ui.screen.routine.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fpstudio.stretchreminder.data.model.BodyPartID
import com.fpstudio.stretchreminder.data.model.Video

@Composable
fun VideosGrid(
    videos: List<Video>,
    groupedByBodyPart: Map<BodyPartID, List<Video>>,
    onVideoClick: (Video) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        groupedByBodyPart.forEach { (bodyPart, bodyPartVideos) ->
            // Section Header
            item(key = "header_${bodyPart.name}") {
                Text(
                    text = bodyPart.displayName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            
            // Videos in 2-column grid
            items(
                items = bodyPartVideos.chunked(2),
                key = { it.firstOrNull()?.id ?: "" }
            ) { rowVideos ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowVideos.forEach { video ->
                        VideoCard(
                            video = video,
                            onClick = { onVideoClick(video) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    
                    // Add empty space if odd number of videos
                    if (rowVideos.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}
