package com.fpstudio.stretchreminder.ui.screen.settings.contract

object SettingsScreenContract {
    
    data class UiState(
        val profile: ProfileState = ProfileState(),
        val plan: PlanState = PlanState(),
        val routinePreferences: RoutinePreferencesState = RoutinePreferencesState(),
        val appSettings: AppSettingsState = AppSettingsState(),
        val subscriptionInfo: com.fpstudio.stretchreminder.domain.model.SubscriptionInfo? = null
    )
    
    data class ProfileState(
        val displayName: String = ""
    )
    
    data class PlanState(
        val planType: String = "Free Account",
        val adsEnabled: Boolean = true
    )
    
    data class RoutinePreferencesState(
        val workPosition: WorkPosition = WorkPosition.SITTING,
        val focusAreas: Set<FocusArea> = emptySet(),
        val workdays: Set<Workday> = emptySet(),
        val startTime: String = "09:00 AM",
        val endTime: String = "05:00 PM"
    )
    
    data class AppSettingsState(
        val notificationsEnabled: Boolean = false
    )
    
    enum class WorkPosition(val displayName: String) {
        SITTING("Sitting"),
        STANDING("Standing"),
        LAYING_DOWN("Laying down")
    }
    
    enum class FocusArea(val displayName: String) {
        NECK("Neck"),
        SHOULDERS("Shoulders"),
        ARMS("Arms"),
        TRAPEZOIDS("Trapezoids"),
        LOWER_BACK("Lower Back"),
        HANDS("Hands"),
        HIP("Hips"),
        LEGS("Legs")
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
        object UpgradeToPremium : Intent()
        object ManageSubscription : Intent()
        object RateApp : Intent()
        object SendFeedback : Intent()
        object SaveChanges : Intent()
        object NavigateBack : Intent()
        object ToggleDarkMode : Intent()
    }
    
    sealed class SideEffect {
        object NavigateBack : SideEffect()
        object NavigateToPremium : SideEffect()
        object NavigateToRateApp : SideEffect()
        object NavigateToFeedback : SideEffect()
        object ShowSaveSuccess : SideEffect()
    }
}
