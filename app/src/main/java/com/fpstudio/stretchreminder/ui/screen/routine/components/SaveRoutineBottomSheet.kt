package com.fpstudio.stretchreminder.ui.screen.routine.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.fpstudio.stretchreminder.data.model.RoutineColor
import com.fpstudio.stretchreminder.data.model.RoutineIcon
import com.fpstudio.stretchreminder.data.model.Video
import com.fpstudio.stretchreminder.ui.screen.routine.SaveRoutineState
import com.fpstudio.stretchreminder.ui.theme.TurquoiseAccent
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState
import androidx.core.graphics.toColorInt

@Composable
fun SaveRoutineBottomSheet(
    state: SaveRoutineState,
    onDismiss: () -> Unit,
    onNameChange: (String) -> Unit,
    onIconSelect: (RoutineIcon) -> Unit,
    onColorSelect: (RoutineColor) -> Unit,
    onReorderVideos: (Int, Int) -> Unit,
    onRemoveVideo: (Video) -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = { /* Prevent dismiss - only via buttons */ },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.32f)),
            contentAlignment = Alignment.BottomCenter
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f),
                shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Scrollable Content
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .padding(top = 16.dp)
                    ) {
                        // Header
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Name Your Routine",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Text(
                                    text = "Saving ${state.videos.size} videos • ${
                                        formatDuration(
                                            state.videos.sumOf { it.duration })
                                    } total",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                            }
                            IconButton(onClick = onDismiss) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close",
                                    tint = Color.Gray
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Routine Name
                        Text(
                            text = "ROUTINE NAME",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = state.name,
                            onValueChange = onNameChange,
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("e.g. Morning Stretch", color = Color.LightGray) },
                            isError = state.isDuplicateName,
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedContainerColor = Color(0xFFF5F5F5),
                                focusedBorderColor = if (state.isDuplicateName) Color.Red else TurquoiseAccent,
                                unfocusedBorderColor = if (state.isDuplicateName) Color.Red else Color(
                                    0xFFE0E0E0
                                ),
                                errorBorderColor = Color.Red
                            ),
                            singleLine = true
                        )
                        if (state.isDuplicateName) {
                            Text(
                                text = "This routine name already exists",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Red,
                                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Icon Selector
                        Text(
                            text = "SELECT ICON",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            RoutineIcon.values().forEach { icon ->
                                IconOption(
                                    icon = icon,
                                    isSelected = state.selectedIcon == icon,
                                    onClick = { onIconSelect(icon) }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Color Selector
                        Text(
                            text = "SELECT COLOR",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            RoutineColor.values().forEach { color ->
                                ColorOption(
                                    modifier = Modifier
                                        .weight(1F)
                                        .aspectRatio(1F),
                                    color = color,
                                    isSelected = state.selectedColor == color,
                                    onClick = { onColorSelect(color) }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Reorder Videos
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "REORDER VIDEOS",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                            Text(
                                text = "Hold to move",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))

                        // Video List with drag-and-drop
                        ReorderableVideoList(
                            videos = state.videos,
                            onReorder = onReorderVideos,
                            onRemoveVideo = onRemoveVideo,
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 300.dp)
                        )
                    }

                    // Fixed Action Buttons at Bottom
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .padding(bottom = 24.dp, top = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Cancel Button
                        OutlinedButton(
                            onClick = onDismiss,
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp),
                            shape = RoundedCornerShape(28.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = Color(0xFFF5F5F5),
                                contentColor = Color.Black
                            ),
                            border = BorderStroke(0.dp, Color.Transparent)
                        ) {
                            Text("Cancel", fontWeight = FontWeight.Bold)
                        }

                        // Save Button
                        Button(
                            onClick = onSave,
                            enabled = state.name.isNotBlank() && !state.isDuplicateName && !state.isSaving,
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp),
                            shape = RoundedCornerShape(28.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = TurquoiseAccent,
                                disabledContainerColor = Color(0xFFE0E0E0)
                            )
                        ) {
                            if (state.isSaving) {
                                CircularProgressIndicator(
                                    color = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            } else {
                                Text("Save", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun IconOption(
    icon: RoutineIcon,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(64.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) TurquoiseAccent.copy(alpha = 0.1f) else Color(0xFFF5F5F5))
            .border(
                width = 2.dp,
                color = if (isSelected) TurquoiseAccent else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = androidx.compose.ui.res.painterResource(id = icon.iconRes),
            contentDescription = icon.displayName,
            tint = if (isSelected) TurquoiseAccent else Color.Gray,
            modifier = Modifier.size(32.dp)
        )
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = TurquoiseAccent,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
                    .size(16.dp)
            )
        }
    }
}

@Composable
private fun ColorOption(
    color: RoutineColor,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(Color(color.hex.toColorInt()))
            .border(
                width = if (isSelected) 3.dp else 0.dp,
                color = if (isSelected) Color.White else Color.Transparent,
                shape = CircleShape
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun ReorderableVideoList(
    videos: List<Video>,
    onReorder: (Int, Int) -> Unit,
    onRemoveVideo: (Video) -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()
    val reorderableState = rememberReorderableLazyListState(
        lazyListState = lazyListState,
        onMove = { from, to ->
            onReorder(from.index, to.index)
        }
    )

    LazyColumn(
        modifier = modifier,
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            count = videos.size,
            key = { index -> videos[index].id }
        ) { index ->
            ReorderableItem(reorderableState, key = videos[index].id) { isDragging ->
                VideoReorderItem(
                    video = videos[index],
                    isDragging = isDragging,
                    onRemove = { onRemoveVideo(videos[index]) },
                    modifier = Modifier.longPressDraggableHandle()
                )
            }
        }
    }
}

@Composable
private fun VideoReorderItem(
    video: Video,
    isDragging: Boolean,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (isDragging) TurquoiseAccent.copy(alpha = 0.2f)
                else Color(0xFFF5F5F5)
            )
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Drag Handle
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = "Drag handle",
            tint = if (isDragging) TurquoiseAccent else Color.Gray,
            modifier = Modifier.size(20.dp)
        )

        // Thumbnail
        AsyncImage(
            model = video.thumbnailUrl,
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        // Video Info
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = video.title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Text(
                text = formatDuration(video.duration),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }

        // Delete Button
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(Color(0xFFE0E0E0))
                .clickable(onClick = onRemove),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Remove video",
                tint = Color.Gray,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

private fun formatDuration(seconds: Int): String {
    val minutes = seconds / 60
    val secs = seconds % 60
    return "$minutes:${String.format("%02d", secs)}"
}
