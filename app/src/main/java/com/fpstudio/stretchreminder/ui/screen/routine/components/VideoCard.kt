package com.fpstudio.stretchreminder.ui.screen.routine.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import com.fpstudio.stretchreminder.data.model.UserType
import com.fpstudio.stretchreminder.data.model.Video
import com.fpstudio.stretchreminder.ui.theme.TurquoiseAccent
import org.koin.compose.koinInject

@Composable
fun VideoCard(
    video: Video,
    userIsPremium: Boolean,
    isTemporarilyUnlocked: Boolean = false,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    imageLoader: ImageLoader = koinInject()
) {
    val isLocked = video.userType == UserType.PREMIUM && !userIsPremium && !isTemporarilyUnlocked
    
    // Shine animation when unlocked
    val shineOffset = remember { androidx.compose.animation.core.Animatable(-1f) }
    
    LaunchedEffect(isTemporarilyUnlocked) {
        if (isTemporarilyUnlocked) {
            // Animate shine from left to right over 2 seconds
            shineOffset.snapTo(-1f)
            shineOffset.animateTo(
                targetValue = 2f,
                animationSpec = androidx.compose.animation.core.tween(
                    durationMillis = 2000,
                    easing = androidx.compose.animation.core.FastOutSlowInEasing
                )
            )
            // After animation completes, select the video
            if (!video.isSelected) {
                onClick()
            }
        }
    }
    
    // Colors
    val premiumYellow = Color(0xFFFDB022)
    val premiumOrange = Color(0xFFFF9500)
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (video.isSelected) 4.dp else 1.dp
        ),
        border = if (isLocked) {
            BorderStroke(2.dp, premiumYellow)
        } else if (video.isSelected) {
            BorderStroke(2.dp, TurquoiseAccent)
        } else null
    ) {
        Column {
            // Thumbnail Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                // Thumbnail Image with caching
                AsyncImage(
                    model = video.thumbnailUrl,
                    contentDescription = video.title,
                    imageLoader = imageLoader,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                
                // Shine effect overlay when unlocking
                if (shineOffset.value > -1f && shineOffset.value < 2f) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                            .background(
                                brush = androidx.compose.ui.graphics.Brush.linearGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.White.copy(alpha = 0.6f),
                                        Color.White.copy(alpha = 0.8f),
                                        Color.White.copy(alpha = 0.6f),
                                        Color.Transparent
                                    ),
                                    start = androidx.compose.ui.geometry.Offset(
                                        x = (shineOffset.value - 0.5f) * 1000f,
                                        y = 0f
                                    ),
                                    end = androidx.compose.ui.geometry.Offset(
                                        x = (shineOffset.value + 0.5f) * 1000f,
                                        y = 1000f
                                    )
                                )
                            )
                    )
                }
                
                if (isLocked) {
                    // Locked Overlay
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.2f))
                    )
                    
                    // Lock Icon in Center
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .align(Alignment.Center)
                            .background(premiumYellow.copy(alpha = 0.9f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Locked",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    
                    // Watch add badge
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(8.dp)
                            .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Watch an AD",
                                color = Color.White,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                } else {
                    // Play Button Overlay (only if not locked)
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .align(Alignment.Center)
                            .background(Color.White.copy(alpha = 0.9f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play",
                            tint = TurquoiseAccent,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    
                    // Badge (Top Right) - only show if badge exists
                    video.badge?.let { badge ->
                        if (badge.name.isNotEmpty()) {
                            Badge(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(8.dp),
                                containerColor = badge.backgroundColor
                            ) {
                                Text(
                                    text = badge.name,
                                    color = Color.White,
                                    style = MaterialTheme.typography.labelSmall,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
            
            // Info Section
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Title
                Text(
                    text = video.title,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black
                )
                
                // Duration and Badge Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // Duration
                    if (isLocked) {
                        Text(
                            text = video.duration.toTimeString(),
                            style = MaterialTheme.typography.bodySmall,
                            color = premiumOrange,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        Text(
                            text = video.duration.toTimeString(),
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

/**
 * Converts duration in seconds to "M:SS" format
 */
private fun Int.toTimeString(): String {
    val minutes = this / 60
    val seconds = this % 60
    return "%d:%02d".format(minutes, seconds)
}
