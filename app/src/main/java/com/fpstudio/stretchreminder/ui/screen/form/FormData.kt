package com.fpstudio.stretchreminder.ui.screen.form

import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.data.model.BodyPart
import com.fpstudio.stretchreminder.data.model.BodyPartID
import com.fpstudio.stretchreminder.data.model.UserAchievement
import com.fpstudio.stretchreminder.ui.component.form.FormUiModel
import com.fpstudio.stretchreminder.ui.composable.question.QuestionID
import com.fpstudio.stretchreminder.ui.composable.question.QuestionUiModel
import kotlin.collections.listOf

fun getForms(): List<FormUiModel> {
    return listOf(
        FormUiModel(
            addNextBtn = true,
            questions = listOf(
                QuestionUiModel.InputText(
                    id = QuestionID.NAME,
                    subtitle1 = "First thing first",
                    question = "What should we call you?",
                    required = true
                )
            )
        ),
        FormUiModel(
            addNextBtn = true,
            questions = listOf(
                QuestionUiModel.MultiChoice(
                    id = QuestionID.ACHIEVEMENT,
                    subtitle1 = "So tell us, {USER_NAME}",
                    question = "What do you want to achieve?",
                    selected = listOf(
                        UserAchievement(
                            iconStr = "🧍‍♂️",
                            title = "All",
                            description = "All"
                        )
                    ),
                    nothingOption = true,
                    options = listOf(
                        UserAchievement(
                            iconStr = "🧍‍♂️",
                            title = "All",
                            description = "All"
                        ),
                        UserAchievement(
                            iconStr = "💪",
                            title = "Reduce Muscle Tension",
                            description = "Reduce Muscle Tension"
                        ),
                        UserAchievement(
                            iconStr = "🧘‍♂️",
                            title = "Improve Posture",
                            description = "Improve Posture"
                        ),
                        UserAchievement(
                            iconStr = "⚡",
                            title = "Increase Energy",
                            description = "Increase Energy"
                        ),
                        UserAchievement(
                            iconStr = "😌",
                            title = "Reduce Stress And Anxiety",
                            description = "Reduce Stress And Anxiety"
                        ),
                        UserAchievement(
                            iconStr = "😴",
                            title = "Improve Sleep quality",
                            description = "Improve Sleep quality"
                        ),
                        UserAchievement(
                            iconStr = "⏱️",
                            title = "Build Healthy Work Breaks",
                            description = "Build Healthy Work Breaks"
                        ),
                        UserAchievement(
                            iconStr = "🤸‍♂️",
                            title = "Enhanced Flexibility and Mobility",
                            description = "Enhanced Flexibility and Mobility"
                        ),
                        UserAchievement(
                            iconStr = "❤️",
                            title = "Prevent Long-Term Health Issues",
                            description = "Prevent Long-Term Health Issues"
                        )
                    )
                )
            )
        ),
        FormUiModel(
            addNextBtn = true,
            questions = listOf(
                QuestionUiModel.CustomBodyQuestion(
                    id = QuestionID.BODY_PARTS,
                    subtitle1 = "Now...,",
                    nothingOption = true,
                    question = "¿Which parts of your body do you want to exercise?",
                    options = listOf(
                        Pair(
                            R.drawable.selected_all, BodyPart(
                                id = BodyPartID.All, "All My Body"
                            )
                        ),
                        Pair(
                            R.drawable.selected_neck,
                            BodyPart(
                                id = BodyPartID.NECK, "Neck"
                            )
                        ),
                        Pair(
                            R.drawable.selected_shoulder,
                            BodyPart(
                                id = BodyPartID.SHOULDERS, "Shoulders"
                            )
                        ),
                        Pair(
                            R.drawable.selected_arms,
                            BodyPart(
                                id = BodyPartID.ARMS, "Arms"
                            )
                        ),
                        Pair(
                            R.drawable.selected_trapezoids,
                            BodyPart(
                                id = BodyPartID.TRAPEZOIDS, "Trapezoids"
                            )
                        ),
                        Pair(
                            R.drawable.selected_lower_back,
                            BodyPart(
                                id = BodyPartID.LOWER_BACK, "Lower Back"
                            )
                        ),
                        Pair(
                            R.drawable.selected_hands,
                            BodyPart(
                                id = BodyPartID.HANDS, "Hands"
                            )
                        ),
                        Pair(
                            R.drawable.selected_hips,
                            BodyPart(
                                id = BodyPartID.HIP, "Hips"
                            )
                        ),
                        Pair(
                            R.drawable.selected_legs,
                            BodyPart(
                                id = BodyPartID.LEGS, "Legs"
                            )
                        ),
                    ),
                    selected = listOf(BodyPartID.NECK, BodyPartID.HANDS)
                )
            )
        ),
        FormUiModel(
            questions = listOf(
                QuestionUiModel.SingleChoice(
                    id = QuestionID.FREQUENCY,
                    subtitle1 = "Building habits that work for you",
                    question = "How often do you stretch during your workday?",
                    subtitle2 = "Your answer heps us to create a personalized plan for you",
                    options = listOf(
                        "Never",
                        "Once a day",
                        "Twice a day",
                        "Three times a day",
                        "Four or more times a day"
                    )
                )
            )
        ),
        FormUiModel(
            addNextBtn = true,
            questions = listOf(
                QuestionUiModel.CustomGenderSingleChoice(
                    id = QuestionID.GENDER,
                    question = "What is your gender?",
                    subtitle2 = "Just to personalize your experience a little more.",
                    male = Pair(R.drawable.man, "Male"),
                    female = Pair(R.drawable.woman, "Female"),
                    preferNotToSay = "Prefer not to say",
                    selected = "Prefer not to say"
                )
            )
        ),
        FormUiModel(
            questions = listOf(
                QuestionUiModel.SingleChoice(
                    id = QuestionID.AGE,
                    subtitle1 = "Let's make this routine truly yours",
                    subtitle2 = "Your age helps you get the right exercises",
                    question = "What is your age?",
                    options = listOf("18 - 35", "36 - 45", "46 - 55", "56 - 69", "Over 70")
                )
            )
        ),
        FormUiModel(
            addNextBtn = true,
            questions = listOf(
                QuestionUiModel.WorkDays(
                    id = QuestionID.WORK_DAYS,
                    question = "Select your work days",
                    subtitle2 = "So we can know which days and when to remind you to stretch",
                    selected = listOf("Mon", "Tue", "Wed", "Thu", "Fri"),
                    days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
                ),
                QuestionUiModel.TimeRange(
                    id = QuestionID.WORK_HOURS,
                    question = "Working Hours"
                )
            )
        ),
        FormUiModel(
            addNextBtn = true,
            questions = listOf(
                QuestionUiModel.ImageSingleChoice(
                    id = QuestionID.MAIN_POSTURE,
                    question = "Select the main posture during your workday",
                    subtitle2 = "Sitting, standing or lying down -- so exercises adapt to your posture",
                    imagesResId = listOf(
                        R.drawable.standing,
                        R.drawable.sitting,
                        R.drawable.laying_down
                    )
                )
            )
        ),
        FormUiModel(
            questions = listOf(
                QuestionUiModel.NotificationPermission(
                    id = QuestionID.NOTIFICATION_PERMISSION,
                    question = "Stay on track with notifications",
                    subtitle2 = "Get gentle reminders to keep up with your stretches and feel your best every day",
                )
            )
        )
    )
}
