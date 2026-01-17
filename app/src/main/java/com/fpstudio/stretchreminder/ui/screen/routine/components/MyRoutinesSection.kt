package com.fpstudio.stretchreminder.ui.screen.routine.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.data.model.Routine
import com.fpstudio.stretchreminder.ui.theme.TurquoiseAccent
import androidx.core.graphics.toColorInt

@Composable
fun MyRoutinesSection(
    savedRoutines: List<Routine>,
    userIsPremium: Boolean,
    selectedRoutineId: Long?,
    onNavigateToCreate: () -> Unit,
    onNavigateToPremium: () -> Unit,
    onRoutineClick: (Long) -> Unit,
    onEditRoutine: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp)
    ) {
        // Header
        MyRoutinesHeader(
            routineCount = savedRoutines.size,
            userIsPremium = userIsPremium,
            onCreateClick = {
                if (userIsPremium) onNavigateToCreate() else onNavigateToPremium()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Content
        when {
            !userIsPremium -> {
                PremiumRoutinesLockedCard(
                    onUnlockClick = onNavigateToPremium
                )
            }

            savedRoutines.isEmpty() -> {
                EmptyRoutinesCard(
                    onStartBuildingClick = onNavigateToCreate
                )
            }

            else -> {
                SavedRoutinesCarousel(
                    routines = savedRoutines,
                    selectedRoutineId = selectedRoutineId,
                    onRoutineClick = onRoutineClick,
                    onEditRoutine = onEditRoutine
                )
            }
        }
    }
}

@Composable
private fun MyRoutinesHeader(
    routineCount: Int,
    userIsPremium: Boolean,
    onCreateClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = "My Routines",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111827) // Gray-900
            )

            if (userIsPremium && routineCount > 0) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "$routineCount custom",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF9CA3AF), // Gray-400
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }

        Surface(
            onClick = onCreateClick,
            color = Color.Transparent
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = TurquoiseAccent,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Create New",
                    style = MaterialTheme.typography.labelLarge,
                    color = TurquoiseAccent,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                if (!userIsPremium) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Locked",
                        tint = TurquoiseAccent,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun PremiumRoutinesLockedCard(
    onUnlockClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            Color(0xFFE5E7EB)
        ) // Gray-200 dashed border logic handled by modifier? No, simple border for now.
        // Dash border is complex in Compose, sticking to solid or dashed via specialized modifier if needed.
        // Image implies dashed. Using solid light gray for simplicity first.
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Gray Icon
            Surface(
                shape = CircleShape,
                color = Color(0xFFF3F4F6), // Gray-100
                modifier = Modifier.size(80.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_premium), // Dumbbell
                        contentDescription = null,
                        tint = Color(0xFFD1D5DB), // Gray-300
                        modifier = Modifier.size(40.dp)
                    )
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Custom Routines",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Tailor your stretching experience to your needs. Create a custom flow just for you.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF6B7280), // Gray-500
                    textAlign = TextAlign.Center
                )
            }

            Button(
                onClick = onUnlockClick,
                colors = ButtonDefaults.buttonColors(containerColor = TurquoiseAccent),
                shape = RoundedCornerShape(50),
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.LockOpen,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Unlock Premium",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyRoutinesCard(
    onStartBuildingClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE5E7EB))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Green Icon
            Surface(
                shape = CircleShape,
                color = Color(0xFFECFDF5), // Green-50
                modifier = Modifier.size(80.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        painter = painterResource(id = R.drawable.routine_icon_2), // Dumbbell
                        contentDescription = null,
                        tint = TurquoiseAccent,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "No Custom Routines Yet",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Tailor your stretching experience to your needs. Create a custom flow just for you.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF6B7280), // Gray-500
                    textAlign = TextAlign.Center
                )
            }

            Button(
                onClick = onStartBuildingClick,
                colors = ButtonDefaults.buttonColors(containerColor = TurquoiseAccent),
                shape = RoundedCornerShape(50),
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = "Start Building",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun SavedRoutinesCarousel(
    routines: List<Routine>,
    selectedRoutineId: Long?,
    onRoutineClick: (Long) -> Unit,
    onEditRoutine: (Long) -> Unit
) {
    val listState = androidx.compose.foundation.lazy.rememberLazyListState()
    
    // Auto-scroll to selected routine when it changes with smooth animation
    LaunchedEffect(selectedRoutineId) {
        selectedRoutineId?.let { routineId ->
            val index = routines.indexOfFirst { it.id == routineId }
            if (index >= 0) {
                // Use smooth scroll for a more visible, slower animation
                listState.animateScrollToItem(index)
            }
        }
    }
    
    LazyRow(
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(end = 16.dp)
    ) {
        items(routines) { routine ->
            SavedRoutineCard(
                routine = routine,
                isSelected = routine.id == selectedRoutineId,
                onClick = { onRoutineClick(routine.id) },
                onEdit = { onEditRoutine(routine.id) }
            )
        }
    }
}

@Composable
private fun SavedRoutineCard(
    routine: Routine,
    isSelected: Boolean,
    onClick: () -> Unit,
    onEdit: () -> Unit
) {
    // Parse hex color
    val cardColor = try {
        Color(routine.color.hex.toColorInt())
    } catch (e: Exception) {
        Color(0xFFE0F2F1) // Default light teal
    }

    val backgroundColor = cardColor.copy(alpha = 0.1f)
    val iconBackgroundColor = cardColor

    Box(
        modifier = Modifier
            .width(160.dp)
            .height(200.dp)
            .then(
                if (isSelected) {
                    Modifier.shadow(
                        elevation = 0.dp,
                        shape = RoundedCornerShape(20.dp),
                        ambientColor = cardColor.copy(alpha = 0.5f),
                        spotColor = cardColor.copy(alpha = 0.5f)
                    )
                } else {
                    Modifier
                }
            )
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = onClick),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = backgroundColor),
            border = androidx.compose.foundation.BorderStroke(
                width = if (isSelected) 3.dp else 1.dp,
                color = if (isSelected) cardColor else cardColor.copy(alpha = 0.3f)
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp
            )
        ) {
            Box(modifier = Modifier
                .fillMaxSize()
            ) {
                // Edit Icon Top Right
                Surface(
                    shape = CircleShape,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .size(28.dp)
                        .clickable(onClick = onEdit)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = Color.Gray,
                        modifier = Modifier
                            .padding(6.dp)
                            .size(14.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Main Icon
                    Surface(
                        shape = CircleShape,
                        color = iconBackgroundColor,
                        modifier = Modifier.size(70.dp),
                        shadowElevation = 4.dp
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                painter = painterResource(id = routine.icon.iconRes),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Name
                    Text(
                        text = routine.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1F2937),
                        maxLines = 1,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Stats Row
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        // Exercises count
                        Icon(
                            painter = painterResource(id = R.drawable.routine_icon_3),
                            contentDescription = null,
                            tint = Color(0xFF6B7280),
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${routine.videoIds.size}",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color(0xFF6B7280),
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "•", color = Color(0xFFD1D5DB))
                        Spacer(modifier = Modifier.width(8.dp))

                        // Duration
                        Icon(
                            painter = painterResource(id = android.R.drawable.ic_menu_recent_history),
                            contentDescription = null,
                            tint = iconBackgroundColor,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        val minutes = routine.totalDuration / 60
                        val seconds = routine.totalDuration % 60
                        Text(
                            text = "$minutes:${seconds.toString().padStart(2, '0')}",
                            style = MaterialTheme.typography.labelMedium,
                            color = iconBackgroundColor,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
