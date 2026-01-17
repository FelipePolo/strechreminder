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
        val endTime: String = "05:00 PM",
        val achievements: List<com.fpstudio.stretchreminder.data.model.UserAchievement> = emptyList()
    )
    
    data class AppSettingsState(
        val notificationsEnabled: Boolean = false
    )
    
    enum class WorkPosition(val displayNameRes: Int) {
        SITTING(com.fpstudio.stretchreminder.R.string.position_sitting),
        STANDING(com.fpstudio.stretchreminder.R.string.position_standing),
        LAYING_DOWN(com.fpstudio.stretchreminder.R.string.position_laying_down)
    }
    
    enum class FocusArea(val displayNameRes: Int) {
        NECK(com.fpstudio.stretchreminder.R.string.body_part_neck),
        SHOULDERS(com.fpstudio.stretchreminder.R.string.body_part_shoulders),
        UPPER_BACK(com.fpstudio.stretchreminder.R.string.body_part_upper_back),
        LOWER_BACK(com.fpstudio.stretchreminder.R.string.body_part_lower_back),
        HANDS(com.fpstudio.stretchreminder.R.string.body_part_hands),
        HIP(com.fpstudio.stretchreminder.R.string.body_part_hips),
        LEGS(com.fpstudio.stretchreminder.R.string.body_part_legs)
    }
    
    enum class Workday(val displayNameRes: Int, val shortNameRes: Int) {
        MONDAY(com.fpstudio.stretchreminder.R.string.day_monday, com.fpstudio.stretchreminder.R.string.day_short_monday),
        TUESDAY(com.fpstudio.stretchreminder.R.string.day_tuesday, com.fpstudio.stretchreminder.R.string.day_short_tuesday),
        WEDNESDAY(com.fpstudio.stretchreminder.R.string.day_wednesday, com.fpstudio.stretchreminder.R.string.day_short_wednesday),
        THURSDAY(com.fpstudio.stretchreminder.R.string.day_thursday, com.fpstudio.stretchreminder.R.string.day_short_thursday),
        FRIDAY(com.fpstudio.stretchreminder.R.string.day_friday, com.fpstudio.stretchreminder.R.string.day_short_friday),
        SATURDAY(com.fpstudio.stretchreminder.R.string.day_saturday, com.fpstudio.stretchreminder.R.string.day_short_saturday),
        SUNDAY(com.fpstudio.stretchreminder.R.string.day_sunday, com.fpstudio.stretchreminder.R.string.day_short_sunday)
    }
    
    sealed class Intent {
        data class SelectWorkPosition(val position: WorkPosition) : Intent()
        data class ToggleFocusArea(val area: FocusArea) : Intent()
        data class ToggleWorkday(val day: Workday) : Intent()
        data class UpdateStartTime(val time: String) : Intent()
        data class UpdateEndTime(val time: String) : Intent()
        data class ToggleNotifications(val enabled: Boolean) : Intent()
        data class UpdateDisplayName(val name: String) : Intent()
        data class ToggleAchievement(val achievement: com.fpstudio.stretchreminder.data.model.UserAchievement) : Intent()
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
