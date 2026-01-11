package com.fpstudio.stretchreminder.ui.screen.routine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.data.model.Video
import com.fpstudio.stretchreminder.ui.composable.button.StretchButtonUiModel
import com.fpstudio.stretchreminder.ui.composable.lupe.LupeScreen
import com.fpstudio.stretchreminder.ui.composable.lupe.LupeUiModel
import com.fpstudio.stretchreminder.ui.screen.routine.components.*
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun RoutineSelectionScreen(
    onNavigateUp: () -> Unit,
    onContinue: (List<Video>) -> Unit,
    onNavigateToMyRoutines: () -> Unit,
    onNavigateToPremium: () -> Unit,
    viewModel: RoutineSelectionViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    RoutineSelectionContent(
        uiState = uiState,
        onIntent = viewModel::handleIntent,
        onNavigateUp = onNavigateUp,
        onContinue = onContinue,
        onNavigateToMyRoutines = onNavigateToMyRoutines,
        onNavigateToPremium = onNavigateToPremium
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RoutineSelectionContent(
    uiState: RoutineSelectionUiState,
    onIntent: (RoutineSelectionIntent) -> Unit,
    onNavigateUp: () -> Unit,
    onContinue: (List<Video>) -> Unit,
    onNavigateToMyRoutines: () -> Unit,
    onNavigateToPremium: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Select Your Routine",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        containerColor = Color(0xFFF5F5F5)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Filter Chips
            FilterChipsRow(
                selectedFilter = uiState.selectedFilter,
                availableBodyParts = uiState.availableBodyParts,
                onFilterSelected = { filter ->
                    onIntent(RoutineSelectionIntent.FilterSelected(filter))
                }
            )

            // Content Area
            when {
                uiState.isLoading -> {
                    LoadingState()
                }

                uiState.error != null -> {
                    ErrorState(
                        errorMessage = uiState.error,
                        onRetry = { onIntent(RoutineSelectionIntent.Retry) }
                    )
                }

                uiState.selectedFilter == VideoFilter.Recommended -> {
                    // Show recommended routines in vertical layout
                    if (uiState.recommendedRoutines.isEmpty()) {
                        EmptyState()
                    } else {
                        RecommendedRoutinesColumn(
                            routines = uiState.recommendedRoutines,
                            selectedRoutineId = uiState.selectedRecommendedRoutineId,
                            userIsPremium = uiState.userIsPremium,
                            onRoutineClick = { routine ->
                                onIntent(RoutineSelectionIntent.RecommendedRoutineSelected(routine))
                            }
                        )
                    }
                }

                uiState.filteredVideos.isEmpty() -> {
                    EmptyState()
                }

                else -> {
                    VideosGrid(
                        videos = uiState.filteredVideos,
                        groupedByBodyPart = uiState.groupedByBodyPart,
                        userIsPremium = uiState.userIsPremium,
                        temporarilyUnlockedVideoIds = uiState.temporarilyUnlockedVideoIds,
                        selectedFilter = uiState.selectedFilter,
                        onVideoClick = { video ->
                            onIntent(RoutineSelectionIntent.VideoSelected(video))
                        }
                    )
                }
            }
        }
        
        // Floating Action Buttons
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.BottomCenter
        ) {
            val totalDuration = uiState.selectedVideos.sumOf { it.duration }
            ActionButtonsRow(
                selectedCount = uiState.selectedVideos.size,
                totalDurationSeconds = totalDuration,
                hasSavedRoutines = uiState.hasSavedRoutines,
                onSaveRoutine = {
                    onIntent(RoutineSelectionIntent.SaveRoutine)
                },
                onMyRoutines = {
                    onIntent(RoutineSelectionIntent.ShowMyRoutinesSheet)
                },
                onStart = {
                    onContinue(uiState.selectedVideos)
                }
            )
        }
        
        // Save Routine Bottom Sheet
        if (uiState.showSaveRoutineSheet) {
            SaveRoutineBottomSheet(
                state = uiState.saveRoutineState,
                onDismiss = { onIntent(RoutineSelectionIntent.HideSaveRoutineSheet) },
                onNameChange = { onIntent(RoutineSelectionIntent.UpdateRoutineName(it)) },
                onIconSelect = { onIntent(RoutineSelectionIntent.SelectRoutineIcon(it)) },
                onColorSelect = { onIntent(RoutineSelectionIntent.SelectRoutineColor(it)) },
                onReorderVideos = { from, to -> onIntent(RoutineSelectionIntent.ReorderVideos(from, to)) },
                onRemoveVideo = { video -> onIntent(RoutineSelectionIntent.RemoveVideoFromRoutine(video)) },
                onSave = { onIntent(RoutineSelectionIntent.ConfirmSaveRoutine) }
            )
        }
        
        // Premium Unlock Bottom Sheet
        if (uiState.showPremiumUnlockSheet) {
            PremiumUnlockBottomSheet(
                onDismiss = {
                    onIntent(RoutineSelectionIntent.HidePremiumUnlockSheet)
                },
                onGoPremium = {
                    onIntent(RoutineSelectionIntent.NavigateToPremium)
                    onNavigateToPremium()
                },
                onWatchAd = {
                    // Simulate watching ad - unlock the pending content
                    uiState.pendingUnlockVideoId?.let { videoId ->
                        onIntent(RoutineSelectionIntent.UnlockVideoTemporarily(videoId))
                    }
                    uiState.pendingUnlockRoutineId?.let { routineId ->
                        onIntent(RoutineSelectionIntent.UnlockRoutineTemporarily(routineId))
                    }
                }
            )
        }
        
        // My Routines Bottom Sheet
        if (uiState.showMyRoutinesSheet) {
            MyRoutinesBottomSheet(
                routines = uiState.savedRoutines,
                selectedRoutineId = uiState.selectedRoutineId,
                onDismiss = { onIntent(RoutineSelectionIntent.HideMyRoutinesSheet) },
                onRoutineSelect = { routineId -> onIntent(RoutineSelectionIntent.SelectRoutine(routineId)) },
                onStartRoutine = { onIntent(RoutineSelectionIntent.StartSelectedRoutine) }
            )
        }
        
        // No Internet Connection Dialog
        if (uiState.showNoInternetDialog) {
            com.fpstudio.stretchreminder.ui.composable.NoInternetConnectionDialog(
                onRetry = { onIntent(RoutineSelectionIntent.CheckInternetConnection) },
                onDismiss = { onIntent(RoutineSelectionIntent.HideNoInternetDialog) }
            )
        }

        
        // Auto-navigate when starting a routine
        LaunchedEffect(uiState.shouldNavigateToExercise) {
            if (uiState.shouldNavigateToExercise && uiState.selectedVideos.isNotEmpty()) {
                onContinue(uiState.selectedVideos)
                // Reset flag
                onIntent(RoutineSelectionIntent.ClearSelection)
            }
        }
    }
}

@Composable
private fun EmptyState(modifier: Modifier = Modifier) {
    LupeScreen(
        state = LupeUiModel(
            icon = R.raw.search,
            title = "We couldn't find any routine",
            description = "please try again later"
        )
    )
}
