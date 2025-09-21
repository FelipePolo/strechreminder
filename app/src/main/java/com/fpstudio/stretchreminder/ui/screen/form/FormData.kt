package com.fpstudio.stretchreminder.ui.screen.form

import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.ui.component.form.FormUiModel
import com.fpstudio.stretchreminder.ui.composable.question.QuestionUiModel
import kotlin.collections.listOf



fun getForms(): List<FormUiModel> {
    return listOf(
        FormUiModel(
            addNextBtn = true,
            questions = listOf(
                QuestionUiModel.InputText(
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
                    subtitle1 = "So tell us",
                    question = "What do you want to achieve?",
                    selected = listOf("🧍‍♂️ ️All"),
                    nothingOption = true,
                    options = listOf(
                        "🧍‍♂️ ️All",
                        "💪  Reduce Muscle Tension",
                        "🧘‍♂️ Improve Posture",
                        "⚡  Increase Energy",
                        "😌  Reduce Stress And Anxiety",
                        "😴  Improve Sleep quality",
                        "⏱️  Build Healthy Work Breaks",
                        "🤸‍♂️  Enhanced Flexibility and Mobility",
                        "❤️  Prevent Long-Term Health Issues"
                    )
                )
            )
        ),
        FormUiModel(
            addNextBtn = true,
            questions = listOf(
                QuestionUiModel.CustomBodyQuestion(
                    subtitle1 = "Now...,",
                    nothingOption = true,
                    question = "¿Which parts of your body do you want to exercise?",
                    options = listOf(
                        Pair(R.drawable.selected_all, "All My Body"),
                        Pair(R.drawable.selected_neck, "Neck"),
                        Pair(R.drawable.selected_shoulder, "Shoulders"),
                        Pair(R.drawable.selected_arms, "Arms"),
                        Pair(R.drawable.selected_trapezoids, "Trapezoids"),
                        Pair(R.drawable.selected_lower_back, "Lower Back"),
                        Pair(R.drawable.selected_hands, "Hands"),
                        Pair(R.drawable.selected_hips, "Hips"),
                        Pair(R.drawable.selected_legs, "Legs"),
                    ),
                    selected = listOf("Neck", "Hands")
                )
            )
        ),
        FormUiModel(
            questions = listOf(
                QuestionUiModel.SingleChoice(
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
                    question = "Select your work days",
                    subtitle2 = "So we can know which days and when to remind you to stretch",
                    selected = listOf("Mon", "Tue", "Wed", "Thu", "Fri"),
                    days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
                ),
                QuestionUiModel.TimeRange(
                    question = "Working Hours"
                )
            )
        ),
        FormUiModel(
            addNextBtn = true,
            questions = listOf(
                QuestionUiModel.ImageSingleChoice(
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
                    question = "Stay on track with notifications",
                    subtitle2 = "Get gentle reminders to keep up with your stretches and feel your best every day",
                )
            )
        )
    )
}
