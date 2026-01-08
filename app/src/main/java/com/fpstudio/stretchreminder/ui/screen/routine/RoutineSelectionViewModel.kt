package com.fpstudio.stretchreminder.ui.screen.routine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fpstudio.stretchreminder.data.model.BodyPartID
import com.fpstudio.stretchreminder.data.model.Video
import com.fpstudio.stretchreminder.domain.usecase.GetVideosUseCase
import com.fpstudio.stretchreminder.ui.screen.routine.components.VideoFilter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RoutineSelectionViewModel(
    private val getVideosUseCase: GetVideosUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(RoutineSelectionUiState())
    val uiState: StateFlow<RoutineSelectionUiState> = _uiState.asStateFlow()
    
    init {
        loadVideos()
    }
    
    fun handleIntent(intent: RoutineSelectionIntent) {
        when (intent) {
            is RoutineSelectionIntent.FilterSelected -> onFilterSelected(intent.filter)
            is RoutineSelectionIntent.VideoSelected -> onVideoSelected(intent.video)
            is RoutineSelectionIntent.Retry -> loadVideos()
        }
    }
    
    private fun loadVideos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            getVideosUseCase("en").fold(
                onSuccess = { videos ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            allVideos = videos,
                            filteredVideos = videos,
                            groupedByBodyPart = groupVideosByBodyParts(videos)
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            error = error.message ?: "Unknown error occurred"
                        )
                    }
                }
            )
        }
    }
    
    private fun onFilterSelected(filter: VideoFilter) {
        _uiState.update { state ->
            val filtered = when (filter) {
                VideoFilter.All -> state.allVideos
                VideoFilter.Recommended -> state.allVideos.filter {
                    // por hacer, filtrar recomendados por partes del cuerpo del usuario
                   true
                }
                is VideoFilter.ByBodyPart -> state.allVideos.filter { 
                    filter.bodyPart in it.bodyParts
                }
            }
            
            state.copy(
                selectedFilter = filter,
                filteredVideos = filtered,
                groupedByBodyPart = groupVideosByBodyParts(filtered)
            )
        }
    }
    
    private fun onVideoSelected(video: Video) {
        _uiState.update { state ->
            // Toggle selection
            val updatedAllVideos = state.allVideos.map {
                if (it.id == video.id) it.copy(isSelected = !it.isSelected) else it
            }
            
            // Apply current filter to get filtered list
            val filtered = when (state.selectedFilter) {
                VideoFilter.All -> updatedAllVideos
                VideoFilter.Recommended -> updatedAllVideos.filter {
                    // por hacer, filtrar recomendados por partes del cuerpo del usuario
                    true
                }
                is VideoFilter.ByBodyPart -> updatedAllVideos.filter { 
                    (state.selectedFilter as VideoFilter.ByBodyPart).bodyPart in it.bodyParts
                }
            }
            
            state.copy(
                allVideos = updatedAllVideos,
                filteredVideos = filtered,
                groupedByBodyPart = groupVideosByBodyParts(filtered),
                selectedVideos = updatedAllVideos.filter { it.isSelected }
            )
        }
    }
    
    /**
     * Groups videos by body parts. Since videos can have multiple body parts,
     * a single video can appear in multiple groups.
     */
    private fun groupVideosByBodyParts(videos: List<Video>): Map<BodyPartID, List<Video>> {
        val grouped = mutableMapOf<BodyPartID, MutableList<Video>>()
        
        videos.forEach { video ->
            video.bodyParts.forEach { bodyPart ->
                grouped.getOrPut(bodyPart) { mutableListOf() }.add(video)
            }
        }
        
        return grouped
    }
}
