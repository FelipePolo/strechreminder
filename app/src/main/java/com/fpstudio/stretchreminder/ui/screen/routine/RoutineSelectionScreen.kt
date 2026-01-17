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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.data.model.Video
import com.fpstudio.stretchreminder.ui.composable.button.StretchButtonUiModel
import com.fpstudio.stretchreminder.ui.composable.lupe.LupeScreen
import com.fpstudio.stretchreminder.ui.composable.lupe.LupeUiModel
import com.fpstudio.stretchreminder.ui.screen.routine.components.*
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalContext
import com.fpstudio.stretchreminder.domain.repository.AdMobRepository

@Composable
fun RoutineSelectionScreen(
    onNavigateUp: () -> Unit,
    onContinue: (List<Video>) -> Unit,
    onNavigateToMyRoutines: () -> Unit,
    onNavigateToPremium: () -> Unit,
    viewModel: RoutineSelectionViewModel = koinViewModel(),
    adMobRepository: AdMobRepository = koinInject()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    
    // Load Ad on entry
    LaunchedEffect(Unit) {
        val TEST_AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917"
        adMobRepository.initialize(context)
        adMobRepository.loadRewardedAd(context, TEST_AD_UNIT_ID)
    }

    RoutineSelectionContent(
        uiState = uiState,
        onIntent = viewModel::handleIntent,
        adMobRepository = adMobRepository,
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
    adMobRepository: AdMobRepository,
    onNavigateUp: () -> Unit,
    onContinue: (List<Video>) -> Unit,
    onNavigateToMyRoutines: () -> Unit,
    onNavigateToPremium: () -> Unit
) {
    val context = LocalContext.current
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
                            savedRoutines = uiState.savedRoutines,
                            selectedRoutineId = uiState.selectedRecommendedRoutineId,
                            selectedCustomRoutineId = uiState.selectedRoutineId,
                            userIsPremium = uiState.userIsPremium,
                            temporarilyUnlockedRoutineIds = uiState.temporarilyUnlockedRoutineIds,
                            bestMatchRoutineId = uiState.bestMatchRoutineId,
                            onRoutineClick = { routine ->
                                onIntent(RoutineSelectionIntent.RecommendedRoutineSelected(routine))
                            },
                            onNavigateToCreate = { 
                                // Initiates creation flow from scratch
                                onIntent(RoutineSelectionIntent.CreateNewRoutine) 
                            },
                            onNavigateToPremium = onNavigateToPremium,
                            onSavedRoutineClick = { routineId ->
                                onIntent(RoutineSelectionIntent.SelectRoutine(routineId))
                            },
                            onEditRoutine = { routineId ->
                                val routine = uiState.savedRoutines.find { it.id == routineId }
                                routine?.let { onIntent(RoutineSelectionIntent.EditRoutine(it)) }
                            }
                        )
                    }
                }

                uiState.filteredVideos.isEmpty() -> {
                    EmptyState()
                }

                else -> {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Help text for individual exercises (not shown on Recommended)
                        if (uiState.selectedFilter != VideoFilter.Recommended) {
                            androidx.compose.material3.Text(
                                text = "Browse individual stretches by body part. Select your favorites to create the perfect routine for you, and save it for later!",
                                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                color = androidx.compose.ui.graphics.Color(0xFF9CA3AF), // Pastel gray
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                                    .padding(bottom = 12.dp),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Start
                            )
                        }
                        
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
                userIsPremium = uiState.userIsPremium,
                hasCustomRoutineSelected = uiState.selectedRoutineId != null,
                onSaveRoutine = {
                    if (uiState.userIsPremium) {
                        onIntent(RoutineSelectionIntent.SaveRoutine)
                    } else {
                        onNavigateToPremium()
                    }
                },
                onStart = {
                    onIntent(RoutineSelectionIntent.StartSelectedRoutine)
                }
            )
        }
        
        // Save Routine Bottom Sheet
        if (uiState.showSaveRoutineSheet) {
            SaveRoutineBottomSheet(
                state = uiState.saveRoutineState,
                allVideos = uiState.allVideos,
                onDismiss = { onIntent(RoutineSelectionIntent.HideSaveRoutineSheet) },
                onNameChange = { onIntent(RoutineSelectionIntent.UpdateRoutineName(it)) },
                onIconSelect = { onIntent(RoutineSelectionIntent.SelectRoutineIcon(it)) },
                onColorSelect = { onIntent(RoutineSelectionIntent.SelectRoutineColor(it)) },
                onReorderVideos = { from, to -> onIntent(RoutineSelectionIntent.ReorderVideos(from, to)) },
                onRemoveVideo = { video -> onIntent(RoutineSelectionIntent.RemoveVideoFromRoutine(video)) },
                onVideoToggle = { video -> onIntent(RoutineSelectionIntent.ToggleVideoInRoutineCreation(video)) },
                onSave = { onIntent(RoutineSelectionIntent.ConfirmSaveRoutine) },
                onDelete = {
                    uiState.saveRoutineState.id?.let { routineId ->
                        onIntent(RoutineSelectionIntent.DeleteRoutine(routineId))
                    }
                }
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
                    val activity = context as? android.app.Activity
                    if (activity != null) {
                        adMobRepository.showRewardedAd(
                            activity = activity,
                            onRewardEarned = {
                                // Unlock the pending content
                                uiState.pendingUnlockVideoId?.let { videoId ->
                                    onIntent(RoutineSelectionIntent.UnlockVideoTemporarily(videoId))
                                }
                                uiState.pendingUnlockRoutineId?.let { routineId ->
                                    onIntent(RoutineSelectionIntent.UnlockRoutineTemporarily(routineId))
                                }
                            },
                            onAdClosed = {
                                // Reload ad for next time
                                val TEST_AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917"
                                adMobRepository.loadRewardedAd(context, TEST_AD_UNIT_ID)
                            },
                            onAdFailedToLoad = {
                                // Handle failure (optional)
                            }
                        )
                    }
                }
            )
        }
        
        // No Internet Connection Dialog
        if (uiState.showNoInternetDialog) {
            com.fpstudio.stretchreminder.ui.composable.NoInternetConnectionDialog(
                onRetry = { onIntent(RoutineSelectionIntent.StartSelectedRoutine) },
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
