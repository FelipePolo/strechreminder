package com.fpstudio.stretchreminder.ui.screen.promises.madeforyou

import com.fpstudio.stretchreminder.data.model.UserAchievement

/**
 * Maps user achievements to MadeForYou cards with appropriate descriptions
 */
fun UserAchievement.toMadeForYouCard(): MadeForYouCard {
    return when (title) {
        "Reduce Muscle Tension" -> MadeForYouCard(
            icon = iconStr,
            title = title,
            description = "Relieve stiffness with guided micro-stretches you can do in minutes."
        )
        "Improve Posture" -> MadeForYouCard(
            icon = iconStr,
            title = title,
            description = "Short routines to correct slouching, tense neck and lower back strain."
        )
        "Increase Energy" -> MadeForYouCard(
            icon = iconStr,
            title = title,
            description = "Boost your energy levels with quick, revitalizing stretch breaks."
        )
        "Reduce Stress And Anxiety" -> MadeForYouCard(
            icon = iconStr,
            title = title,
            description = "Calm your mind and body with gentle, stress-relieving movements."
        )
        "Improve Sleep quality" -> MadeForYouCard(
            icon = iconStr,
            title = title,
            description = "Wind down with relaxing stretches that prepare your body for better rest."
        )
        "Build Healthy Work Breaks" -> MadeForYouCard(
            icon = iconStr,
            title = title,
            description = "Smart reminders and 2-5 min breaks that won't interrupt your flow."
        )
        "Enhanced Flexibility and Mobility" -> MadeForYouCard(
            icon = iconStr,
            title = title,
            description = "Increase your range of motion with targeted flexibility exercises."
        )
        "Prevent Long-Term Health Issues" -> MadeForYouCard(
            icon = iconStr,
            title = title,
            description = "Protect your body from chronic pain and long-term strain with daily care."
        )
        "All" -> MadeForYouCard(
            icon = iconStr,
            title = "Complete Wellness",
            description = "A holistic approach to improve your overall health and well-being."
        )
        else -> MadeForYouCard(
            icon = iconStr,
            title = title,
            description = "Personalized stretches designed to help you reach your goals."
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
            icon = "⏱️",
            title = "Build Healthy Work Breaks",
            description = "Smart reminders and 2-5 min breaks that won't interrupt your flow."
        ),
        MadeForYouCard(
            icon = "🧘",
            title = "Improve Posture",
            description = "Short routines to correct slouching, tense neck and lower back strain."
        ),
        MadeForYouCard(
            icon = "💪",
            title = "Reduce Muscle Tension",
            description = "Relieve stiffness with guided micro-stretches you can do in minutes."
        )
    )
    
    // Filter out "All" option and take first 3
    val userCards = achievements
        .filter { it.title != "All" }
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
