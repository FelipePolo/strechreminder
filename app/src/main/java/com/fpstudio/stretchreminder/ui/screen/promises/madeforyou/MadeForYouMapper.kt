package com.fpstudio.stretchreminder.ui.screen.promises.madeforyou

import com.fpstudio.stretchreminder.data.model.UserAchievement

import com.fpstudio.stretchreminder.R

/**
 * Maps user achievements to MadeForYou cards with appropriate descriptions
 */
fun UserAchievement.toMadeForYouCard(): MadeForYouCard {
    return when (title) {
        R.string.achievement_muscle_tension_title -> MadeForYouCard(
            icon = R.string.achievement_icon_muscle,
            title = R.string.mfy_card_reduce_tension_title,
            description = R.string.mfy_card_reduce_tension_desc
        )
        R.string.achievement_posture_title -> MadeForYouCard(
            icon = R.string.achievement_icon_posture,
            title = R.string.mfy_card_improve_posture_title,
            description = R.string.mfy_card_improve_posture_desc
        )
        R.string.achievement_energy_title -> MadeForYouCard(
            icon = R.string.achievement_icon_energy,
            title = R.string.mfy_card_increase_energy_title,
            description = R.string.mfy_card_increase_energy_desc
        )
        R.string.achievement_stress_title -> MadeForYouCard(
            icon = R.string.achievement_icon_stress,
            title = R.string.mfy_card_reduce_stress_title,
            description = R.string.mfy_card_reduce_stress_desc
        )
        R.string.achievement_sleep_title -> MadeForYouCard(
            icon = R.string.achievement_icon_sleep,
            title = R.string.mfy_card_improve_sleep_title,
            description = R.string.mfy_card_improve_sleep_desc
        )
        R.string.achievement_breaks_title -> MadeForYouCard(
            icon = R.string.achievement_icon_breaks,
            title = R.string.mfy_card_build_breaks_title,
            description = R.string.mfy_card_build_breaks_desc
        )
        R.string.achievement_flexibility_title -> MadeForYouCard(
            icon = R.string.achievement_icon_flexibility,
            title = R.string.mfy_card_flexibility_title,
            description = R.string.mfy_card_flexibility_desc
        )
        R.string.achievement_health_title -> MadeForYouCard(
            icon = R.string.achievement_icon_health,
            title = R.string.mfy_card_prevent_issues_title,
            description = R.string.mfy_card_prevent_issues_desc
        )
        else -> MadeForYouCard(
            icon = R.string.achievement_icon_default,
            title = R.string.mfy_card_default_title,
            description = R.string.mfy_card_default_desc
        )
    }
}

/**
 * Creates a list of MadeForYou cards from user achievements
 * Takes the first 3 achievements, or uses defaults if less than 3
 */
fun createMadeForYouCards(achievements: List<UserAchievement>): Triple<MadeForYouCard, MadeForYouCard, MadeForYouCard> {
    val defaultCards = listOf(
        MadeForYouCard(
            icon = R.string.achievement_icon_breaks,
            title = R.string.mfy_card_build_breaks_title,
            description = R.string.mfy_card_build_breaks_desc
        ),
        MadeForYouCard(
            icon = R.string.achievement_icon_posture,
            title = R.string.mfy_card_improve_posture_title,
            description = R.string.mfy_card_improve_posture_desc
        ),
        MadeForYouCard(
            icon = R.string.achievement_icon_muscle,
            title = R.string.mfy_card_reduce_tension_title,
            description = R.string.mfy_card_reduce_tension_desc
        )
    )
    
    // Filter out "All" option and take first 3
    val userCards = achievements
        .take(3)
        .map { it.toMadeForYouCard() }
    
    // Fill with defaults if needed
    val finalCards = when (userCards.size) {
        0 -> defaultCards
        1 -> listOf(userCards[0], defaultCards[1], defaultCards[2])
        2 -> listOf(userCards[0], userCards[1], defaultCards[2])
        else -> userCards
    }
    
    return Triple(finalCards[0], finalCards[1], finalCards[2])
}
