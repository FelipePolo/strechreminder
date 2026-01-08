package com.fpstudio.stretchreminder.ui.screen.routine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fpstudio.stretchreminder.data.model.BodyPartID
import com.fpstudio.stretchreminder.data.model.Routine
import com.fpstudio.stretchreminder.data.model.RoutineColor
import com.fpstudio.stretchreminder.data.model.RoutineIcon
import com.fpstudio.stretchreminder.data.model.Video
import com.fpstudio.stretchreminder.data.model.VideoVisibility
import com.fpstudio.stretchreminder.domain.usecase.GetSavedRoutinesUseCase
import com.fpstudio.stretchreminder.domain.usecase.GetVideosUseCase
import com.fpstudio.stretchreminder.domain.usecase.SaveRoutineUseCase
import com.fpstudio.stretchreminder.domain.repository.RoutineRepository
import com.fpstudio.stretchreminder.ui.screen.routine.components.VideoFilter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RoutineSelectionViewModel(
    private val getVideosUseCase: GetVideosUseCase,
    private val saveRoutineUseCase: SaveRoutineUseCase,
    private val getSavedRoutinesUseCase: GetSavedRoutinesUseCase,
    private val routineRepository: RoutineRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(RoutineSelectionUiState())
    val uiState: StateFlow<RoutineSelectionUiState> = _uiState.asStateFlow()
    
    init {
        loadVideos()
        loadSavedRoutines()
        checkUserPremiumStatus()
    }

    private fun checkUserPremiumStatus() {
        // TODO: Replace with actual UserPreferenceRepository call
        // For now, setting to TRUE to verify the fix as requested by user
        // showing that premium users see normal cards
        _uiState.update { it.copy(userIsPremium = true) }
    }
    
    fun handleIntent(intent: RoutineSelectionIntent) {
        when (intent) {
            is RoutineSelectionIntent.FilterSelected -> onFilterSelected(intent.filter)
            is RoutineSelectionIntent.VideoSelected -> onVideoSelected(intent.video)
            is RoutineSelectionIntent.Retry -> loadVideos()
            is RoutineSelectionIntent.SaveRoutine -> onSaveRoutine()
            is RoutineSelectionIntent.NavigateToMyRoutines -> onNavigateToMyRoutines()
            is RoutineSelectionIntent.ShowSaveRoutineSheet -> onShowSaveRoutineSheet()
            is RoutineSelectionIntent.HideSaveRoutineSheet -> onHideSaveRoutineSheet()
            is RoutineSelectionIntent.UpdateRoutineName -> onUpdateRoutineName(intent.name)
            is RoutineSelectionIntent.SelectRoutineIcon -> onSelectRoutineIcon(intent.icon)
            is RoutineSelectionIntent.SelectRoutineColor -> onSelectRoutineColor(intent.color)
            is RoutineSelectionIntent.ReorderVideos -> onReorderVideos(intent.fromIndex, intent.toIndex)
            is RoutineSelectionIntent.ConfirmSaveRoutine -> onConfirmSaveRoutine()
            is RoutineSelectionIntent.ClearSelection -> onClearSelection()
            is RoutineSelectionIntent.RemoveVideoFromRoutine -> onRemoveVideoFromRoutine(intent.video)
            is RoutineSelectionIntent.ShowMyRoutinesSheet -> onShowMyRoutinesSheet()
            is RoutineSelectionIntent.HideMyRoutinesSheet -> onHideMyRoutinesSheet()
            is RoutineSelectionIntent.SelectRoutine -> onSelectRoutine(intent.routineId)
            is RoutineSelectionIntent.StartSelectedRoutine -> onStartSelectedRoutine()
        }
    }
    
    private fun loadVideos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            getVideosUseCase("en").fold(
                onSuccess = { videos ->
                    // Filter to show only public videos
                    val publicVideos = videos.filter { it.visibility == VideoVisibility.PUBLIC }
                    
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            allVideos = publicVideos,
                            filteredVideos = publicVideos,
                            groupedByBodyPart = groupVideosByBodyParts(publicVideos)
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
    
    private fun onSaveRoutine() {
        // Show bottom sheet with current selected videos
        onShowSaveRoutineSheet()
    }
    
    private fun onNavigateToMyRoutines() {
        // TODO: Implement navigation to My Routines screen
        // This will be handled via a callback to the screen
    }
    
    private fun onShowSaveRoutineSheet() {
        _uiState.update { state ->
            state.copy(
                showSaveRoutineSheet = true,
                saveRoutineState = SaveRoutineState(
                    videos = state.selectedVideos
                )
            )
        }
    }
    
    private fun onHideSaveRoutineSheet() {
        _uiState.update { it.copy(showSaveRoutineSheet = false) }
    }
    
    private fun onUpdateRoutineName(name: String) {
        _uiState.update { state ->
            state.copy(
                saveRoutineState = state.saveRoutineState.copy(name = name)
            )
        }
        // Check for duplicate name
        checkDuplicateName(name)
    }
    
    private fun checkDuplicateName(name: String) {
        if (name.isBlank()) {
            _uiState.update { state ->
                state.copy(
                    saveRoutineState = state.saveRoutineState.copy(isDuplicateName = false)
                )
            }
            return
        }
        
        viewModelScope.launch {
            val isDuplicate = routineRepository.routineNameExists(name)
            _uiState.update { state ->
                state.copy(
                    saveRoutineState = state.saveRoutineState.copy(isDuplicateName = isDuplicate)
                )
            }
        }
    }
    
    private fun onSelectRoutineIcon(icon: RoutineIcon) {
        _uiState.update { state ->
            state.copy(
                saveRoutineState = state.saveRoutineState.copy(selectedIcon = icon)
            )
        }
    }
    
    private fun onSelectRoutineColor(color: RoutineColor) {
        _uiState.update { state ->
            state.copy(
                saveRoutineState = state.saveRoutineState.copy(selectedColor = color)
            )
        }
    }
    
    private fun onReorderVideos(fromIndex: Int, toIndex: Int) {
        _uiState.update { state ->
            val videos = state.saveRoutineState.videos.toMutableList()
            val item = videos.removeAt(fromIndex)
            videos.add(toIndex, item)
            state.copy(
                saveRoutineState = state.saveRoutineState.copy(videos = videos)
            )
        }
    }
    
    private fun onConfirmSaveRoutine() {
        val state = _uiState.value.saveRoutineState
        
        // Validate
        if (state.name.isBlank() || state.isDuplicateName) {
            return
        }
        
        _uiState.update { it.copy(saveRoutineState = it.saveRoutineState.copy(isSaving = true)) }
        
        viewModelScope.launch {
            val totalDuration = state.videos.sumOf { it.duration }
            val routine = Routine(
                name = state.name,
                icon = state.selectedIcon,
                color = state.selectedColor,
                videoIds = state.videos.map { it.id },
                totalDuration = totalDuration
            )
            
            saveRoutineUseCase(routine).fold(
                onSuccess = {
                    // Success - close bottom sheet
                    _uiState.update { 
                        it.copy(
                            showSaveRoutineSheet = false,
                            saveRoutineState = SaveRoutineState()
                        )
                    }
                    // Clear all selected videos
                    onClearSelection()
                },
                onFailure = { error ->
                    _uiState.update { state ->
                        state.copy(
                            saveRoutineState = state.saveRoutineState.copy(
                                isSaving = false,
                                error = error.message
                            )
                        )
                    }
                }
            )
        }
    }
    
    private fun onClearSelection() {
        _uiState.update { state ->
            state.copy(
                selectedVideos = emptyList(),
                shouldNavigateToExercise = false
            )
        }
    }
    
    private fun onRemoveVideoFromRoutine(video: Video) {
        _uiState.update { state ->
            state.copy(
                saveRoutineState = state.saveRoutineState.copy(
                    videos = state.saveRoutineState.videos.filter { it.id != video.id }
                )
            )
        }
    }
    
    private fun loadSavedRoutines() {
        viewModelScope.launch {
            getSavedRoutinesUseCase().collect { routines ->
                _uiState.update { state ->
                    state.copy(
                        savedRoutines = routines,
                        hasSavedRoutines = routines.isNotEmpty()
                    )
                }
            }
        }
    }
    
    private fun onShowMyRoutinesSheet() {
        _uiState.update { state ->
            state.copy(
                showMyRoutinesSheet = true,
                selectedRoutineId = null
            )
        }
    }
    
    private fun onHideMyRoutinesSheet() {
        _uiState.update { state ->
            state.copy(
                showMyRoutinesSheet = false,
                selectedRoutineId = null
            )
        }
    }
    
    private fun onSelectRoutine(routineId: Long) {
        _uiState.update { state ->
            state.copy(selectedRoutineId = routineId)
        }
    }
    
    private fun onStartSelectedRoutine() {
        val selectedRoutineId = _uiState.value.selectedRoutineId ?: return
        val routine = _uiState.value.savedRoutines.find { it.id == selectedRoutineId } ?: return
        
        // Get all videos from state
        val allVideos = _uiState.value.allVideos
        
        // Filter videos that match the routine's videoIds
        val routineVideos = routine.videoIds.mapNotNull { videoId ->
            allVideos.find { it.id == videoId }
        }
        
        // Select these videos in the state (this will trigger navigation via onContinue)
        _uiState.update { state ->
            state.copy(
                selectedVideos = routineVideos,
                showMyRoutinesSheet = false,
                selectedRoutineId = null,
                shouldNavigateToExercise = true
            )
        }
    }
}
