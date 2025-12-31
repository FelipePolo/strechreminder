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
import java.util.Calendar
import java.util.Date
import java.util.Locale

class SettingsScreenViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val getSubscriptionInfoUseCase: com.fpstudio.stretchreminder.domain.usecase.GetSubscriptionInfoUseCase
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
            val subscriptionInfo = getSubscriptionInfoUseCase()
            val loadedState = mapUserToUiState(user).copy(
                subscriptionInfo = subscriptionInfo
            )
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
                endTime = formatTimestampToTime(user.endTime),
                achievements = user.achievement
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
                // Handle short format (3 letters)
                "MON" -> Workday.MONDAY
                "TUE" -> Workday.TUESDAY
                "WED" -> Workday.WEDNESDAY
                "THU" -> Workday.THURSDAY
                "FRI" -> Workday.FRIDAY
                "SAT" -> Workday.SATURDAY
                "SUN" -> Workday.SUNDAY
                // Handle full names for backward compatibility
                "MONDAY" -> Workday.MONDAY
                "TUESDAY" -> Workday.TUESDAY
                "WEDNESDAY" -> Workday.WEDNESDAY
                "THURSDAY" -> Workday.THURSDAY
                "FRIDAY" -> Workday.FRIDAY
                "SATURDAY" -> Workday.SATURDAY
                "SUNDAY" -> Workday.SUNDAY
                else -> null
            }
        }.toSet()
    }
    
    private fun formatTimestampToTime(timestamp: Long): String {
        if (timestamp == 0L) return "09:00 AM"
        val date = Date(timestamp)
        // Use Locale.US to ensure consistent AM/PM formatting
        val format = SimpleDateFormat("hh:mm a", Locale.US)
        return format.format(date)
    }
    
    private fun parseTimeToTimestamp(timeString: String): Long {
        return try {
            // Use Locale.US to ensure consistent AM/PM parsing
            val format = SimpleDateFormat("hh:mm a", Locale.US)
            format.isLenient = false
            val parsedDate = format.parse(timeString)
            
            if (parsedDate != null) {
                // Create a calendar with today's date and the parsed time
                val calendar = Calendar.getInstance().apply {
                    time = parsedDate
                    // Keep the hour and minute from parsed time
                    val hour = get(Calendar.HOUR_OF_DAY)
                    val minute = get(Calendar.MINUTE)
                    
                    // Set to today's date with the parsed time
                    timeInMillis = System.currentTimeMillis()
                    set(Calendar.HOUR_OF_DAY, hour)
                    set(Calendar.MINUTE, minute)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }
                calendar.timeInMillis
            } else {
                0L
            }
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
        // Use short 3-letter format to match the format used in the form
        return workdays.map { workday ->
            when (workday) {
                Workday.MONDAY -> "Mon"
                Workday.TUESDAY -> "Tue"
                Workday.WEDNESDAY -> "Wed"
                Workday.THURSDAY -> "Thu"
                Workday.FRIDAY -> "Fri"
                Workday.SATURDAY -> "Sat"
                Workday.SUNDAY -> "Sun"
            }
        }
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
            notificationPermission = currentState.appSettings.notificationsEnabled,
            achievement = currentState.routinePreferences.achievements
        )
        
        saveUserUseCase(updatedUser)
        // Update initialState after successful save to prevent change detection issues
        initialState = currentState
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
            
            is Intent.ToggleAchievement -> {
                val currentAchievements = _uiState.value.routinePreferences.achievements.toMutableList()
                val existing = currentAchievements.find { it.title == intent.achievement.title }
                if (existing != null) {
                    currentAchievements.remove(existing)
                } else {
                    currentAchievements.add(intent.achievement)
                }
                _uiState.value = _uiState.value.copy(
                    routinePreferences = _uiState.value.routinePreferences.copy(
                        achievements = currentAchievements
                    )
                )
            }
            
            Intent.UpgradeToPremium -> {
                viewModelScope.launch {
                    _sideEffect.emit(SideEffect.NavigateToPremium)
                }
            }
            
            Intent.ManageSubscription -> {
                viewModelScope.launch {
                    // This will be handled in the UI to open Google Play subscriptions
                    _sideEffect.emit(SideEffect.NavigateToRateApp) // Reusing for now, will add specific one if needed
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
                    _sideEffect.emit(SideEffect.NavigateBack)
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
