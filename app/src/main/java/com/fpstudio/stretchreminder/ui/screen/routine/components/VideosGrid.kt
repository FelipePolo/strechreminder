package com.fpstudio.stretchreminder.ui.screen.routine.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
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
    userIsPremium: Boolean,
    selectedFilter: VideoFilter,
    onVideoClick: (Video) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        groupedByBodyPart.forEach { (bodyPart, bodyPartVideos) ->
            // Skip the "All" body part group - we don't want to show it as a section
            if (bodyPart == BodyPartID.All) {
                return@forEach
            }

            // Section Header - show headers when filtering by ALL or Recommended to group by body part
            // Hide header when filtering by specific body part (already filtered)
            val shouldShowHeader = selectedFilter == VideoFilter.All ||
                    selectedFilter == VideoFilter.Recommended

            if (shouldShowHeader) {
                item(key = "header_${bodyPart.name}") {
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(
                            animationSpec = tween(
                                durationMillis = 500,
                                easing = FastOutSlowInEasing
                            )
                        ) + slideInVertically(
                            animationSpec = tween(
                                durationMillis = 500,
                                easing = FastOutSlowInEasing
                            ),
                            initialOffsetY = { -it / 2 }
                        ),
                        exit = fadeOut(
                            animationSpec = tween(
                                durationMillis = 400,
                                easing = FastOutSlowInEasing
                            )
                        ) + slideOutVertically(
                            animationSpec = tween(
                                durationMillis = 400,
                                easing = FastOutSlowInEasing
                            ),
                            targetOffsetY = { -it / 2 }
                        )
                    ) {
                        Text(
                            text = bodyPart.displayName,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }

            // Videos in 2-column grid
            items(
                items = bodyPartVideos.chunked(2),
                key = { "${bodyPart.name}_${it.firstOrNull()?.id ?: ""}" }
            ) { rowVideos ->
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(
                        animationSpec = tween(
                            durationMillis = 600,
                            easing = FastOutSlowInEasing
                        )
                    ) + slideInVertically(
                        animationSpec = tween(
                            durationMillis = 600,
                            easing = FastOutSlowInEasing
                        ),
                        initialOffsetY = { it }
                    ),
                    exit = fadeOut(
                        animationSpec = tween(
                            durationMillis = 400,
                            easing = FastOutSlowInEasing
                        )
                    ) + slideOutVertically(
                        animationSpec = tween(
                            durationMillis = 400,
                            easing = FastOutSlowInEasing
                        ),
                        targetOffsetY = { -it / 2 }
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        rowVideos.forEach { video ->
                            VideoCard(
                                video = video,
                                userIsPremium = userIsPremium,
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
}
