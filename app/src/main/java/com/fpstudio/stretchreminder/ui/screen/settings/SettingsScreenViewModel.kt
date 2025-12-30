package com.fpstudio.stretchreminder.ui.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fpstudio.stretchreminder.data.model.BodyPartID
import com.fpstudio.stretchreminder.data.model.User
import com.fpstudio.stretchreminder.domain.usecase.GetUserUseCase
import com.fpstudio.stretchreminder.domain.usecase.SaveUserUseCase
import com.fpstudio.stretchreminder.ui.screen.settings.contract.SettingsScreenContract.Intent
import com.fpstudio.stretchreminder.ui.screen.settings.contract.SettingsScreenContract.SideEffect
import com.fpstudio.stretchreminder.ui.screen.settings.contract.SettingsScreenContract.UiState
import com.fpstudio.stretchreminder.ui.screen.settings.contract.SettingsScreenContract.WorkPosition
import com.fpstudio.stretchreminder.ui.screen.settings.contract.SettingsScreenContract.FocusArea
import com.fpstudio.stretchreminder.ui.screen.settings.contract.SettingsScreenContract.Workday
import com.fpstudio.stretchreminder.ui.screen.settings.contract.SettingsScreenContract.ProfileState
import com.fpstudio.stretchreminder.ui.screen.settings.contract.SettingsScreenContract.RoutinePreferencesState
import com.fpstudio.stretchreminder.ui.screen.settings.contract.SettingsScreenContract.AppSettingsState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SettingsScreenViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val saveUserUseCase: SaveUserUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    
    private var initialState: UiState? = null
    
    private val _sideEffect = MutableSharedFlow<SideEffect>()
    val sideEffect: SharedFlow<SideEffect> = _sideEffect.asSharedFlow()
    
    init {
        loadUserData()
    }
    
    private fun loadUserData() {
        viewModelScope.launch {
            val user = getUserUseCase() ?: return@launch
            val loadedState = mapUserToUiState(user)
            _uiState.value = loadedState
            // Save initial state after loading data
            initialState = loadedState
        }
    }
    
    fun hasUnsavedChanges(): Boolean {
        return initialState != null && _uiState.value != initialState
    }
    
    private fun mapUserToUiState(user: User): UiState {
        return UiState(
            profile = ProfileState(
                displayName = user.name
            ),
            routinePreferences = RoutinePreferencesState(
                workPosition = mapMainPostureToWorkPosition(user.mainPosture),
                focusAreas = mapBodyPartsToFocusAreas(user.bodyParts),
                workdays = mapWorkDaysToWorkdays(user.workDays),
                startTime = formatTimestampToTime(user.startTime),
                endTime = formatTimestampToTime(user.endTime)
            ),
            appSettings = AppSettingsState(
                notificationsEnabled = user.notificationPermission
            )
        )
    }
    
    private fun mapMainPostureToWorkPosition(mainPosture: Int): WorkPosition {
        return when (mainPosture) {
            0 -> WorkPosition.SITTING
            1 -> WorkPosition.STANDING
            2 -> WorkPosition.LAYING_DOWN
            else -> WorkPosition.SITTING
        }
    }
    
    private fun mapBodyPartsToFocusAreas(bodyParts: List<BodyPartID>): Set<FocusArea> {
        return bodyParts.mapNotNull { bodyPart ->
            when (bodyPart) {
                BodyPartID.NECK -> FocusArea.NECK
                BodyPartID.SHOULDERS -> FocusArea.SHOULDERS
                BodyPartID.ARMS -> FocusArea.ARMS
                BodyPartID.TRAPEZOIDS -> FocusArea.TRAPEZOIDS
                BodyPartID.LOWER_BACK -> FocusArea.LOWER_BACK
                BodyPartID.HANDS -> FocusArea.HANDS
                BodyPartID.HIP -> FocusArea.HIP
                BodyPartID.LEGS -> FocusArea.LEGS
                else -> null
            }
        }.toSet()
    }
    
    private fun mapWorkDaysToWorkdays(workDays: List<String>): Set<Workday> {
        return workDays.mapNotNull { day ->
            when (day.uppercase()) {
                "MON", "M" -> Workday.MONDAY
                "TUE", "T" -> Workday.TUESDAY
                "WED", "W" -> Workday.WEDNESDAY
                "THU", "TH" -> Workday.THURSDAY
                "FRI", "F" -> Workday.FRIDAY
                "SAT", "S", "SA" -> Workday.SATURDAY
                "SUN", "SU" -> Workday.SUNDAY
                else -> null
            }
        }.toSet()
    }
    
    private fun formatTimestampToTime(timestamp: Long): String {
        if (timestamp == 0L) return "09:00 AM"
        val date = Date(timestamp)
        val format = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return format.format(date)
    }
    
    private fun parseTimeToTimestamp(timeString: String): Long {
        return try {
            val format = SimpleDateFormat("hh:mm a", Locale.getDefault())
            format.parse(timeString)?.time ?: 0L
        } catch (e: Exception) {
            0L
        }
    }
    
    private fun mapWorkPositionToMainPosture(workPosition: WorkPosition): Int {
        return when (workPosition) {
            WorkPosition.SITTING -> 0
            WorkPosition.STANDING -> 1
            WorkPosition.LAYING_DOWN -> 2
        }
    }
    
    private fun mapFocusAreasToBodyParts(focusAreas: Set<FocusArea>): List<BodyPartID> {
        return focusAreas.mapNotNull { area ->
            when (area) {
                FocusArea.NECK -> BodyPartID.NECK
                FocusArea.SHOULDERS -> BodyPartID.SHOULDERS
                FocusArea.ARMS -> BodyPartID.ARMS
                FocusArea.TRAPEZOIDS -> BodyPartID.TRAPEZOIDS
                FocusArea.LOWER_BACK -> BodyPartID.LOWER_BACK
                FocusArea.HANDS -> BodyPartID.HANDS
                FocusArea.HIP -> BodyPartID.HIP
                FocusArea.LEGS -> BodyPartID.LEGS
            }
        }
    }
    
    private fun mapWorkdaysToWorkDays(workdays: Set<Workday>): List<String> {
        return workdays.map { it.displayName }
    }
    
    private suspend fun saveCurrentState() {
        val currentState = _uiState.value
        val user = getUserUseCase() ?: User()
        
        val updatedUser = user.copy(
            name = currentState.profile.displayName,
            mainPosture = mapWorkPositionToMainPosture(currentState.routinePreferences.workPosition),
            bodyParts = mapFocusAreasToBodyParts(currentState.routinePreferences.focusAreas),
            workDays = mapWorkdaysToWorkDays(currentState.routinePreferences.workdays),
            startTime = parseTimeToTimestamp(currentState.routinePreferences.startTime),
            endTime = parseTimeToTimestamp(currentState.routinePreferences.endTime),
            notificationPermission = currentState.appSettings.notificationsEnabled
        )
        
        saveUserUseCase(updatedUser)
    }
    
    fun handleIntent(intent: Intent) {
        when (intent) {
            is Intent.SelectWorkPosition -> {
                _uiState.value = _uiState.value.copy(
                    routinePreferences = _uiState.value.routinePreferences.copy(
                        workPosition = intent.position
                    )
                )
            }
            
            is Intent.ToggleFocusArea -> {
                val currentAreas = _uiState.value.routinePreferences.focusAreas.toMutableSet()
                if (currentAreas.contains(intent.area)) {
                    currentAreas.remove(intent.area)
                } else {
                    currentAreas.add(intent.area)
                }
                _uiState.value = _uiState.value.copy(
                    routinePreferences = _uiState.value.routinePreferences.copy(
                        focusAreas = currentAreas
                    )
                )
            }
            
            is Intent.ToggleWorkday -> {
                val currentDays = _uiState.value.routinePreferences.workdays.toMutableSet()
                if (currentDays.contains(intent.day)) {
                    currentDays.remove(intent.day)
                } else {
                    currentDays.add(intent.day)
                }
                _uiState.value = _uiState.value.copy(
                    routinePreferences = _uiState.value.routinePreferences.copy(
                        workdays = currentDays
                    )
                )
            }
            
            is Intent.UpdateStartTime -> {
                _uiState.value = _uiState.value.copy(
                    routinePreferences = _uiState.value.routinePreferences.copy(
                        startTime = intent.time
                    )
                )
            }
            
            is Intent.UpdateEndTime -> {
                _uiState.value = _uiState.value.copy(
                    routinePreferences = _uiState.value.routinePreferences.copy(
                        endTime = intent.time
                    )
                )
            }
            
            is Intent.ToggleNotifications -> {
                _uiState.value = _uiState.value.copy(
                    appSettings = _uiState.value.appSettings.copy(
                        notificationsEnabled = intent.enabled
                    )
                )
            }
            
            is Intent.UpdateDisplayName -> {
                _uiState.value = _uiState.value.copy(
                    profile = _uiState.value.profile.copy(
                        displayName = intent.name
                    )
                )
            }
            
            Intent.UpgradeToPremium -> {
                viewModelScope.launch {
                    _sideEffect.emit(SideEffect.NavigateToPremium)
                }
            }
            
            Intent.RateApp -> {
                viewModelScope.launch {
                    _sideEffect.emit(SideEffect.NavigateToRateApp)
                }
            }
            
            Intent.SendFeedback -> {
                viewModelScope.launch {
                    _sideEffect.emit(SideEffect.NavigateToFeedback)
                }
            }
            
            Intent.SaveChanges -> {
                viewModelScope.launch {
                    saveCurrentState()
                    _sideEffect.emit(SideEffect.ShowSaveSuccess)
                }
            }
            
            Intent.NavigateBack -> {
                viewModelScope.launch {
                    _sideEffect.emit(SideEffect.NavigateBack)
                }
            }
            
            Intent.ToggleDarkMode -> {
                // TODO: Implement dark mode toggle
            }
        }
    }
}
