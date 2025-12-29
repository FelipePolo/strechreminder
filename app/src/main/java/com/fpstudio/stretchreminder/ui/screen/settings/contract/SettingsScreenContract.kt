package com.fpstudio.stretchreminder.ui.screen.settings.contract

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

object SettingsScreenContract {
    
    data class UiState(
        val profile: ProfileState = ProfileState(),
        val plan: PlanState = PlanState(),
        val routinePreferences: RoutinePreferencesState = RoutinePreferencesState(),
        val appSettings: AppSettingsState = AppSettingsState()
    )
    
    data class ProfileState(
        val displayName: String = "Alex Smith"
    )
    
    data class PlanState(
        val planType: String = "Free Account",
        val adsEnabled: Boolean = true
    )
    
    data class RoutinePreferencesState(
        val workPosition: WorkPosition = WorkPosition.SITTING,
        val focusAreas: Set<FocusArea> = setOf(FocusArea.NECK, FocusArea.SHOULDERS),
        val workdays: Set<Workday> = setOf(
            Workday.MONDAY,
            Workday.TUESDAY,
            Workday.WEDNESDAY,
            Workday.FRIDAY
        ),
        val startTime: String = "01:00 AM",
        val endTime: String = "01:00 AM"
    )
    
    data class AppSettingsState(
        val notificationsEnabled: Boolean = true
    )
    
    enum class WorkPosition(val displayName: String) {
        SITTING("Sitting"),
        STANDING("Standing"),
        ACTIVE("Active")
    }
    
    enum class FocusArea(val displayName: String) {
        NECK("Neck"),
        SHOULDERS("Shoulders"),
        LOWER_BACK("Lower Back"),
        HANDS("Hands"),
        LEGS("Legs"),
        ABDOMEN("Abdomen")
    }
    
    enum class Workday(val displayName: String, val shortName: String) {
        MONDAY("Monday", "M"),
        TUESDAY("Tuesday", "T"),
        WEDNESDAY("Wednesday", "W"),
        THURSDAY("Thursday", "T"),
        FRIDAY("Friday", "F"),
        SATURDAY("Saturday", "S"),
        SUNDAY("Sunday", "S")
    }
    
    sealed class Intent {
        data class SelectWorkPosition(val position: WorkPosition) : Intent()
        data class ToggleFocusArea(val area: FocusArea) : Intent()
        data class ToggleWorkday(val day: Workday) : Intent()
        data class UpdateStartTime(val time: String) : Intent()
        data class UpdateEndTime(val time: String) : Intent()
        data class ToggleNotifications(val enabled: Boolean) : Intent()
        data class UpdateDisplayName(val name: String) : Intent()
        object EditProfile : Intent()
        object UpgradeToPremium : Intent()
        object RateApp : Intent()
        object SendFeedback : Intent()
        object SaveChanges : Intent()
        object NavigateBack : Intent()
        object ToggleDarkMode : Intent()
    }
    
    sealed class SideEffect {
        object NavigateBack : SideEffect()
        object NavigateToEditProfile : SideEffect()
        object NavigateToPremium : SideEffect()
        object NavigateToRateApp : SideEffect()
        object NavigateToFeedback : SideEffect()
        object ShowSaveSuccess : SideEffect()
    }
}
