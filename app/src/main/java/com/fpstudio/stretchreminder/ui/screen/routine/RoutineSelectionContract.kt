package com.fpstudio.stretchreminder.ui.screen.routine

import com.fpstudio.stretchreminder.data.model.BodyPartID
import com.fpstudio.stretchreminder.data.model.Routine
import com.fpstudio.stretchreminder.data.model.RoutineColor
import com.fpstudio.stretchreminder.data.model.RoutineIcon
import com.fpstudio.stretchreminder.data.model.Video
import com.fpstudio.stretchreminder.ui.screen.routine.components.VideoFilter

data class RoutineSelectionUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val allVideos: List<Video> = emptyList(),
    val filteredVideos: List<Video> = emptyList(),
    val selectedVideos: List<Video> = emptyList(),
    val groupedByBodyPart: Map<BodyPartID, List<Video>> = emptyMap(),
    val selectedFilter: VideoFilter = VideoFilter.Recommended,
    val availableBodyParts: List<BodyPartID> = BodyPartID.values().filter { it != BodyPartID.All }.toList(),
    val hasSavedRoutines: Boolean = true, // TODO: Replace with actual saved routines check
    val showSaveRoutineSheet: Boolean = false,
    val saveRoutineState: SaveRoutineState = SaveRoutineState(),
    val showMyRoutinesSheet: Boolean = false,
    val savedRoutines: List<Routine> = emptyList(),
    val selectedRoutineId: Long? = null,
    val shouldNavigateToExercise: Boolean = false,
    val userIsPremium: Boolean = false,
    val showPremiumUnlockSheet: Boolean = false,
    val showNoInternetDialog: Boolean = false,
    val recommendedRoutines: List<com.fpstudio.stretchreminder.data.model.RecommendedRoutine> = emptyList(),
    val selectedRecommendedRoutineId: Int? = null,
    val temporarilyUnlockedVideoIds: Set<String> = emptySet(),
    val temporarilyUnlockedRoutineIds: Set<Int> = emptySet(),
    val pendingUnlockVideoId: String? = null,
    val pendingUnlockRoutineId: Int? = null,
    val bestMatchRoutineId: Int? = null
)

data class SaveRoutineState(
    val name: String = "",
    val selectedIcon: RoutineIcon = RoutineIcon.STRETCH,
    val selectedColor: RoutineColor = RoutineColor.TURQUOISE,
    val videos: List<Video> = emptyList(),
    val isSaving: Boolean = false,
    val error: String? = null,
    val isDuplicateName: Boolean = false
)

sealed class RoutineSelectionIntent {
    data class FilterSelected(val filter: VideoFilter) : RoutineSelectionIntent()
    data class VideoSelected(val video: Video) : RoutineSelectionIntent()
    data class RecommendedRoutineSelected(val routine: com.fpstudio.stretchreminder.data.model.RecommendedRoutine) : RoutineSelectionIntent()
    object Retry : RoutineSelectionIntent()
    object SaveRoutine : RoutineSelectionIntent()
    object NavigateToMyRoutines : RoutineSelectionIntent()
    object ShowSaveRoutineSheet : RoutineSelectionIntent()
    object HideSaveRoutineSheet : RoutineSelectionIntent()
    data class UpdateRoutineName(val name: String) : RoutineSelectionIntent()
    data class SelectRoutineIcon(val icon: RoutineIcon) : RoutineSelectionIntent()
    data class SelectRoutineColor(val color: RoutineColor) : RoutineSelectionIntent()
    data class ReorderVideos(val fromIndex: Int, val toIndex: Int) : RoutineSelectionIntent()
    object ConfirmSaveRoutine : RoutineSelectionIntent()
    object ClearSelection : RoutineSelectionIntent()
    data class RemoveVideoFromRoutine(val video: Video) : RoutineSelectionIntent()
    data class ToggleVideoInRoutineCreation(val video: Video) : RoutineSelectionIntent()
    object ShowMyRoutinesSheet : RoutineSelectionIntent()
    object HideMyRoutinesSheet : RoutineSelectionIntent()
    data class SelectRoutine(val routineId: Long) : RoutineSelectionIntent()
    object StartSelectedRoutine : RoutineSelectionIntent()
    object HidePremiumUnlockSheet : RoutineSelectionIntent()
    object NavigateToPremium : RoutineSelectionIntent()
    data class UnlockVideoTemporarily(val videoId: String) : RoutineSelectionIntent()
    data class UnlockRoutineTemporarily(val routineId: Int) : RoutineSelectionIntent()
    object ClearTemporaryUnlocks : RoutineSelectionIntent()
    object CheckInternetConnection : RoutineSelectionIntent()
    object HideNoInternetDialog : RoutineSelectionIntent()
}
