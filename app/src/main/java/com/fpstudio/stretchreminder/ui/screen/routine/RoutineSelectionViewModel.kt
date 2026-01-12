package com.fpstudio.stretchreminder.ui.screen.routine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fpstudio.stretchreminder.data.model.BodyPartID
import com.fpstudio.stretchreminder.data.model.Routine
import com.fpstudio.stretchreminder.data.model.RoutineColor
import com.fpstudio.stretchreminder.data.model.RoutineIcon
import com.fpstudio.stretchreminder.data.model.Video
import com.fpstudio.stretchreminder.data.model.VideoVisibility
import com.fpstudio.stretchreminder.domain.usecase.CheckEntitlementUseCase
import com.fpstudio.stretchreminder.domain.usecase.CheckNetworkConnectivityUseCase
import com.fpstudio.stretchreminder.domain.usecase.GetBodyPartsUseCase
import com.fpstudio.stretchreminder.domain.usecase.GetSavedRoutinesUseCase
import com.fpstudio.stretchreminder.domain.usecase.GetVideosUseCase
import com.fpstudio.stretchreminder.domain.usecase.SaveRoutineUseCase
import com.fpstudio.stretchreminder.domain.usecase.GetUserUseCase
import com.fpstudio.stretchreminder.domain.repository.RoutineRepository
import com.fpstudio.stretchreminder.domain.repository.TemporaryAccessRepository
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
    private val routineRepository: RoutineRepository,
    private val checkEntitlementUseCase: CheckEntitlementUseCase,
    private val getBodyPartsUseCase: GetBodyPartsUseCase,
    private val checkNetworkConnectivityUseCase: CheckNetworkConnectivityUseCase,
    private val temporaryAccessRepository: TemporaryAccessRepository,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(RoutineSelectionUiState())
    val uiState: StateFlow<RoutineSelectionUiState> = _uiState.asStateFlow()
    
    init {
        loadBodyParts()
        loadVideos()
        loadSavedRoutines()
        checkUserPremiumStatus()
        checkUserPremiumStatus()
        checkInternetConnectionForFreeUsers()
        observeTemporaryUnlocks()
    }

    private fun observeTemporaryUnlocks() {
        viewModelScope.launch {
            temporaryAccessRepository.temporarilyUnlockedVideoIds.collect { ids ->
                _uiState.update { it.copy(temporarilyUnlockedVideoIds = ids) }
            }
        }
        viewModelScope.launch {
            temporaryAccessRepository.temporarilyUnlockedRoutineIds.collect { ids ->
                _uiState.update { it.copy(temporarilyUnlockedRoutineIds = ids) }
            }
        }
    }
    
    private fun checkUserPremiumStatus() {
        // Check actual entitlement status
        val isPremium = checkEntitlementUseCase()
        _uiState.update { it.copy(userIsPremium = isPremium) }
    }
    
    fun handleIntent(intent: RoutineSelectionIntent) {
        when (intent) {
            is RoutineSelectionIntent.FilterSelected -> onFilterSelected(intent.filter)
            is RoutineSelectionIntent.VideoSelected -> onVideoSelected(intent.video)
            is RoutineSelectionIntent.RecommendedRoutineSelected -> onRecommendedRoutineSelected(intent.routine)
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
            is RoutineSelectionIntent.HidePremiumUnlockSheet -> onHidePremiumUnlockSheet()
            is RoutineSelectionIntent.NavigateToPremium -> onNavigateToPremium()
            is RoutineSelectionIntent.UnlockVideoTemporarily -> onUnlockVideoTemporarily(intent.videoId)
            is RoutineSelectionIntent.UnlockRoutineTemporarily -> onUnlockRoutineTemporarily(intent.routineId)
            is RoutineSelectionIntent.ClearTemporaryUnlocks -> onClearTemporaryUnlocks()
            is RoutineSelectionIntent.CheckInternetConnection -> checkInternetConnectionForFreeUsers()
            is RoutineSelectionIntent.HideNoInternetDialog -> onHideNoInternetDialog()
            is RoutineSelectionIntent.ToggleVideoInRoutineCreation -> onToggleVideoInRoutineCreation(intent.video)
        }
    }
    
    // ... helper function definition ...
    private fun onToggleVideoInRoutineCreation(video: Video) {
        _uiState.update { state ->
            val currentVideos = state.saveRoutineState.videos
            val isSelected = currentVideos.any { it.id == video.id }
            val newVideos = if (isSelected) {
                currentVideos.filter { it.id != video.id }
            } else {
                currentVideos + video
            }
            state.copy(
                saveRoutineState = state.saveRoutineState.copy(videos = newVideos)
            )
        }
    }
    
    private fun onHidePremiumUnlockSheet() {
        _uiState.update { it.copy(showPremiumUnlockSheet = false) }
    }
    
    private fun onNavigateToPremium() {
        // This will be handled by the screen layer
        // Just hide the sheet
        _uiState.update { it.copy(showPremiumUnlockSheet = false) }
    }
    
    private fun onUnlockVideoTemporarily(videoId: String) {
        temporaryAccessRepository.unlockVideo(videoId)
        _uiState.update { state ->
            state.copy(showPremiumUnlockSheet = false, pendingUnlockVideoId = null)
        }
    }
    
    private fun onUnlockRoutineTemporarily(routineId: Int) {
        temporaryAccessRepository.unlockRoutine(routineId)
        _uiState.update { state ->
            state.copy(showPremiumUnlockSheet = false, pendingUnlockRoutineId = null)
        }
    }
    
    private fun onClearTemporaryUnlocks() {
        temporaryAccessRepository.clearAll()
        _uiState.update { state ->
            state.copy(pendingUnlockVideoId = null, pendingUnlockRoutineId = null)
        }
    }
    
    private fun onHideNoInternetDialog() {
        _uiState.update { it.copy(showNoInternetDialog = false) }
    }
    
    private fun checkInternetConnectionForFreeUsers() {
        // Only check internet for free users
        if (!_uiState.value.userIsPremium) {
            val hasInternet = checkNetworkConnectivityUseCase()
            _uiState.update { it.copy(showNoInternetDialog = !hasInternet) }
        }
    }
    
    private fun loadBodyParts() {
        viewModelScope.launch {
            val bodyParts = getBodyPartsUseCase("en")
            _uiState.update { it.copy(availableBodyParts = bodyParts) }
        }
    }

    
    private fun loadVideos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            getVideosUseCase("en").fold(
                onSuccess = { result ->
                    // Filter to show only public videos
                    val publicVideos = result.videos.filter { it.visibility == VideoVisibility.PUBLIC }
                    
                    // Reorder recommended routines
                    var recommendedRoutines = result.recommendedRoutines.toMutableList()
                    val isPremium = checkEntitlementUseCase()
                    val user = getUserUseCase()
                    
                    // Priority 1: Best matching PREMIUM routine at index 0
                    var bestMatchId: Int? = null
                    if (user != null && user.bodyParts.isNotEmpty()) {
                        val userBodyPartsSet = user.bodyParts.toSet()
                        
                        val bestPremiumMatch = recommendedRoutines
                            .filter { it.userType == com.fpstudio.stretchreminder.data.model.UserType.PREMIUM }
                            .maxWithOrNull(
                                compareBy<com.fpstudio.stretchreminder.data.model.RecommendedRoutine> { routine ->
                                    // 1. Maximize intersection with user preferences
                                    routine.bodyparts.intersect(userBodyPartsSet).size
                                }.thenByDescending { routine ->
                                    // 2. Minimize extra parts (negative of count of parts NOT in user preferences)
                                    // We use thenByDescending because we want the "smallest number of extras" to be "better".
                                    // compareByDescending means compare(a, b) = b.compareTo(a).
                                    // With positive extraParts: compare(6, 3) = 3.compareTo(6) = -1.
                                    // So 6 comes "before" 3. 6 < 3.
                                    // maxWith picks the "Largest" element. 3 is larger than 6.
                                    // So the one with FEWER extras (3) is selected as MAX.
                                    val extraParts = routine.bodyparts.size - routine.bodyparts.intersect(userBodyPartsSet).size
                                    extraParts
                                }
                            )
                        
                        if (bestPremiumMatch != null) {
                            recommendedRoutines.remove(bestPremiumMatch)
                            recommendedRoutines.add(0, bestPremiumMatch)
                            bestMatchId = bestPremiumMatch.id
                        }
                    }
                    
                    // Priority 2: For Free users, ensure a Free routine is at index 1 (if available)
                    if (!isPremium && recommendedRoutines.size >= 2) {
                        val firstFreeIndex = recommendedRoutines.indexOfFirst { 
                            it.userType == com.fpstudio.stretchreminder.data.model.UserType.FREE 
                        }
                        
                        if (firstFreeIndex != -1 && firstFreeIndex != 1) {
                            val item = recommendedRoutines.removeAt(firstFreeIndex)
                            // Safe check again just in case
                            if (recommendedRoutines.size >= 1) {
                                recommendedRoutines.add(1, item)
                            }
                        }
                    }
                    
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            allVideos = publicVideos,
                            filteredVideos = publicVideos,
                            groupedByBodyPart = groupVideosByBodyParts(publicVideos),
                            recommendedRoutines = recommendedRoutines,
                            bestMatchRoutineId = bestMatchId
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
            // For Recommended filter, we don't need to filter videos
            // The UI will show recommendedRoutines instead
            if (filter == VideoFilter.Recommended) {
                return@update state.copy(selectedFilter = filter)
            }
            
            val filtered = when (filter) {
                VideoFilter.All -> state.allVideos
                VideoFilter.Recommended -> state.allVideos // Won't be used
                is VideoFilter.ByBodyPart -> state.allVideos.filter { 
                    filter.bodyPart in it.bodyParts
                }
            }
            
            // When filtering by specific body part, only group by that body part
            // to avoid showing videos multiple times
            val grouped = when (filter) {
                is VideoFilter.ByBodyPart -> mapOf(filter.bodyPart to filtered)
                else -> groupVideosByBodyParts(filtered)
            }
            
            state.copy(
                selectedFilter = filter,
                filteredVideos = filtered,
                groupedByBodyPart = grouped
            )
        }
    }
    
    private fun onVideoSelected(video: Video) {
        _uiState.update { state ->
            // Check if video is premium and user is free AND not temporarily unlocked
            if (video.userType == com.fpstudio.stretchreminder.data.model.UserType.PREMIUM && 
                !state.userIsPremium && 
                video.id !in state.temporarilyUnlockedVideoIds) {
                return@update state.copy(
                    showPremiumUnlockSheet = true,
                    pendingUnlockVideoId = video.id,
                    pendingUnlockRoutineId = null
                )
            }

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
                selectedVideos = updatedAllVideos.filter { it.isSelected },
                selectedRecommendedRoutineId = null // Clear recommended routine selection
            )
        }
    }
    
    

    
    private fun onRecommendedRoutineSelected(routine: com.fpstudio.stretchreminder.data.model.RecommendedRoutine) {
        _uiState.update { state ->
            // Check if routine is premium and user is free AND not temporarily unlocked
            if (routine.userType == com.fpstudio.stretchreminder.data.model.UserType.PREMIUM && 
                !state.userIsPremium && 
                routine.id !in state.temporarilyUnlockedRoutineIds) {
                return@update state.copy(
                    showPremiumUnlockSheet = true,
                    pendingUnlockRoutineId = routine.id,
                    pendingUnlockVideoId = null
                )
            }

            // Toggle selection: if already selected, deselect; otherwise select this one
            val newSelectedId = if (state.selectedRecommendedRoutineId == routine.id) {
                null
            } else {
                routine.id
            }
            
            // Clear all individual video selections when selecting a recommended routine
            val clearedVideos = state.allVideos.map { it.copy(isSelected = false) }
            
            // Update selected videos based on routine selection
            val selectedVideos = if (newSelectedId != null) {
                routine.videos
            } else {
                emptyList()
            }

            state.copy(
                selectedRecommendedRoutineId = newSelectedId,
                selectedVideos = selectedVideos,
                allVideos = clearedVideos,
                filteredVideos = when (state.selectedFilter) {
                    VideoFilter.All -> clearedVideos
                    VideoFilter.Recommended -> clearedVideos
                    is VideoFilter.ByBodyPart -> clearedVideos.filter { 
                        (state.selectedFilter as VideoFilter.ByBodyPart).bodyPart in it.bodyParts
                    }
                },
                shouldNavigateToExercise = false
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
        val state = _uiState.value
        
        // Case 1: Saved Routine selected (via My Routines sheet)
        if (state.selectedRoutineId != null) {
            val routine = state.savedRoutines.find { it.id == state.selectedRoutineId } ?: return
            
            // Get all videos from state
            val allVideos = state.allVideos
            
            // Filter videos that match the routine's videoIds
            val routineVideos = routine.videoIds.mapNotNull { videoId ->
                allVideos.find { it.id == videoId }
            }
            
            // Clear temporary unlocks immediately before navigation
            temporaryAccessRepository.clearAll()
            
            _uiState.update { 
                it.copy(
                    selectedVideos = routineVideos,
                    shouldNavigateToExercise = true,
                    showMyRoutinesSheet = false,
                    selectedRoutineId = null
                )
            }
            return
        }
        
        // Case 2: Manual Video Selection
        if (state.selectedVideos.isNotEmpty()) {
            // Clear temporary unlocks immediately before navigation
            temporaryAccessRepository.clearAll()
            
            _uiState.update { it.copy(shouldNavigateToExercise = true) }
            return
        }
    }
}
