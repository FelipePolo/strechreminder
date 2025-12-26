package com.fpstudio.stretchreminder.ui.screen.routine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.data.model.Video
import com.fpstudio.stretchreminder.ui.composable.button.StretchButtonUiModel
import com.fpstudio.stretchreminder.ui.composable.lupe.LupeScreen
import com.fpstudio.stretchreminder.ui.composable.lupe.LupeUiModel
import com.fpstudio.stretchreminder.ui.screen.routine.components.*
import org.koin.androidx.compose.koinViewModel

@Composable
fun RoutineSelectionScreen(
    viewModel: RoutineSelectionViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    onContinue: (List<Video>) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    RoutineSelectionContent(
        uiState = uiState,
        onIntent = viewModel::handleIntent,
        onBackClick = onBackClick,
        onContinue = onContinue
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RoutineSelectionContent(
    uiState: RoutineSelectionUiState,
    onIntent: (RoutineSelectionIntent) -> Unit,
    onBackClick: () -> Unit,
    onContinue: (List<Video>) -> Unit
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
                    IconButton(onClick = onBackClick) {
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
        bottomBar = {
            val totalDuration = uiState.selectedVideos.sumOf { it.duration }
            ContinueButton(
                enabled = uiState.selectedVideos.isNotEmpty(),
                selectedCount = uiState.selectedVideos.size,
                totalDurationSeconds = totalDuration,
                onClick = {
                    onContinue(uiState.selectedVideos)
                }
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

                uiState.filteredVideos.isEmpty() -> {
                    EmptyState()
                }

                else -> {
                    VideosGrid(
                        videos = uiState.filteredVideos,
                        groupedByBodyPart = uiState.groupedByBodyPart,
                        onVideoClick = { video ->
                            onIntent(RoutineSelectionIntent.VideoSelected(video))
                        }
                    )
                }
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
//    Box(
//        modifier = modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        Text(
//            text = "No exercises found",
//            style = MaterialTheme.typography.bodyLarge,
//            color = Color.Gray
//        )
//    }
}
