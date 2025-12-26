package com.fpstudio.stretchreminder.ui.screen.routine

import com.fpstudio.stretchreminder.data.model.BodyPartID
import com.fpstudio.stretchreminder.data.model.Video
import com.fpstudio.stretchreminder.ui.screen.routine.components.VideoFilter

data class RoutineSelectionUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val allVideos: List<Video> = emptyList(),
    val filteredVideos: List<Video> = emptyList(),
    val selectedVideos: List<Video> = emptyList(),
    val groupedByBodyPart: Map<BodyPartID, List<Video>> = emptyMap(),
    val selectedFilter: VideoFilter = VideoFilter.All
)

sealed class RoutineSelectionIntent {
    data class FilterSelected(val filter: VideoFilter) : RoutineSelectionIntent()
    data class VideoSelected(val video: Video) : RoutineSelectionIntent()
    object Retry : RoutineSelectionIntent()
}
