package com.fpstudio.stretchreminder.ui.screen.routine.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.fpstudio.stretchreminder.data.model.RecommendedRoutine
import com.fpstudio.stretchreminder.data.model.UserType
import com.fpstudio.stretchreminder.ui.theme.TurquoiseAccent

@Composable
fun RecommendedRoutineCard(
    routine: RecommendedRoutine,
    isSelected: Boolean = false,
    userIsPremium: Boolean = false,
    isTemporarilyUnlocked: Boolean = false,
    isBestMatch: Boolean = false,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isPremium = routine.userType == UserType.PREMIUM
    // Show normal premium lock only if it is premium AND user is NOT premium AND NOT temporarily unlocked AND NOT best match
    // If it IS best match, we show the special badge instead (but it's still locked in behavior unless we want to unlock it?)
    // Re-reading request: "change the text of the lock icon and PREMIUM... for a star icon and 'based on your preferences'"
    // This implies visually it changes, but functionally it might still be premium?
    // Usually "Based on your preferences" implies a recommendation.
    // If the user is NOT premium, they still can't play it unless unlocked. 
    // The visual request is to REPLACE the lock icon. 
    // So we hide the standard lock UI if isBestMatch is true.
    
    val showPremiumLock = isPremium && !userIsPremium && !isTemporarilyUnlocked
    // We want to show the Best Match badge regardless of premium status to highlight the recommendation
    // unless the user specifically requested it to be a "replacement for lock" ONLY. 
    // But since they complained about not seeing it as premium user, they likely want to see it.
    // However, for Free users it acts as a lock replacement (visually). Functionally logic handles the lock.
    val showBestMatchBadge = isBestMatch
    
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
            if (!isSelected) {
                onClick()
            }
        }
    }
    
    val borderModifier = when {
        isSelected -> Modifier.border(
            width = 3.dp,
            color = TurquoiseAccent,
            shape = RoundedCornerShape(16.dp)
        )
        showPremiumLock -> Modifier.border(
            width = 2.dp,
            brush = Brush.linearGradient(
                colors = listOf(
                    Color(0xFFFFD700), // Gold
                    Color(0xFFFFA500)  // Orange
                )
            ),
            shape = RoundedCornerShape(16.dp)
        )
        else -> Modifier
    }
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .then(borderModifier)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 4.dp else 2.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            // Thumbnail with duration badge and premium lock
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                AsyncImage(
                    model = routine.thumbnail,
                    contentDescription = routine.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                
                // Shine effect overlay when unlocking
                if (shineOffset.value > -1f && shineOffset.value < 2f) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                brush = Brush.linearGradient(
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
                
                // Duration badge
                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = Color.Black.copy(alpha = 0.7f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            painter = androidx.compose.ui.res.painterResource(
                                id = android.R.drawable.ic_menu_recent_history
                            ),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "${routine.duration / 60} min",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                
                // Premium lock icon - only show for free users (regular)
                if (showPremiumLock) {
                    Surface(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(56.dp),
                        shape = CircleShape,
                        color = Color(0xFFFFF8DC) // Cream color
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Premium",
                            tint = Color(0xFFfcbf24), // Gold
                            modifier = Modifier
                                .padding(14.dp)
                                .size(28.dp)
                        )
                    }
                }
                
                // Premium badge - only show for free users
                if (showPremiumLock && !showBestMatchBadge) {
                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFfcbf24) // Gold
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(12.dp)
                            )
                            Text(
                                text = "PREMIUM",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp
                            )
                        }
                    }
                } else if (showBestMatchBadge) {
                     Surface(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = TurquoiseAccent
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(12.dp)
                            )
                            Text(
                                text = "Based on your preferences",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Routine name and exercise count
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = routine.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )
                
                // Exercise count badge
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (isPremium) {
                        Color(0xFFFFF4E0) // Light orange for premium
                    } else {
                        Color(0xFFE0F7F4) // Light turquoise for free
                    }
                ) {
                    Text(
                        text = "${routine.quantity} Exercises",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (isPremium) {
                            Color(0xFFFF8C00) // Dark orange for premium
                        } else {
                            TurquoiseAccent
                        },
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Tags
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                routine.tags.take(2).forEach { tag ->
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (isPremium) {
                            Color(0xFFFFF8DC).copy(alpha = 0.5f) // Light cream for premium
                        } else {
                            Color(0xFFF5F5F5) // Light gray for free
                        }
                    ) {
                        Text(
                            text = tag.name,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}
