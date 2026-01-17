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
                    subtitle1 = R.string.form_question_name_subtitle,
                    question = R.string.form_question_name,
                    required = true
                )
            )
        ),
        FormUiModel(
            addNextBtn = true,
            questions = listOf(
                QuestionUiModel.MultiChoice(
                    id = QuestionID.ACHIEVEMENT,
                    subtitle1 = R.string.form_question_achievement_subtitle,
                    question = R.string.form_question_achievement,
                    selected = listOf(
                        UserAchievement(
                            iconStr = "💪",
                            title = R.string.achievement_muscle_tension_title,
                            description = R.string.achievement_muscle_tension_desc
                        ),
                        UserAchievement(
                            iconStr = "😌",
                            title = R.string.achievement_stress_title,
                            description = R.string.achievement_stress_desc
                        )
                    ),
                    options = listOf(
                        UserAchievement(
                            iconStr = "💪",
                            title = R.string.achievement_muscle_tension_title,
                            description = R.string.achievement_muscle_tension_desc
                        ),
                        UserAchievement(
                            iconStr = "🧘‍♂️",
                            title = R.string.achievement_posture_title,
                            description = R.string.achievement_posture_desc
                        ),
                        UserAchievement(
                            iconStr = "⚡",
                            title = R.string.achievement_energy_title,
                            description = R.string.achievement_energy_desc
                        ),
                        UserAchievement(
                            iconStr = "😌",
                            title = R.string.achievement_stress_title,
                            description = R.string.achievement_stress_desc
                        ),
                        UserAchievement(
                            iconStr = "😴",
                            title = R.string.achievement_sleep_title,
                            description = R.string.achievement_sleep_desc
                        ),
                        UserAchievement(
                            iconStr = "⏱️",
                            title = R.string.achievement_breaks_title,
                            description = R.string.achievement_breaks_desc
                        ),
                        UserAchievement(
                            iconStr = "🤸‍♂️",
                            title = R.string.achievement_flexibility_title,
                            description = R.string.achievement_flexibility_desc
                        ),
                        UserAchievement(
                            iconStr = "❤️",
                            title = R.string.achievement_health_title,
                            description = R.string.achievement_health_desc
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
                    subtitle1 = R.string.form_question_body_subtitle,
                    question = R.string.form_question_body,
                    options = listOf(
                        Pair(
                            R.drawable.selected_neck,
                            BodyPart(
                                id = BodyPartID.NECK, name = R.string.body_part_neck
                            )
                        ),
                        Pair(
                            R.drawable.selected_shoulder,
                            BodyPart(
                                id = BodyPartID.SHOULDERS, name = R.string.body_part_shoulders
                            )
                        ),
                        Pair(
                            R.drawable.selected_upper_back,
                            BodyPart(
                                id = BodyPartID.UPPER_BACK, name = R.string.body_part_upper_back
                            )
                        ),
                        Pair(
                            R.drawable.selected_lower_back,
                            BodyPart(
                                id = BodyPartID.LOWER_BACK, name = R.string.body_part_lower_back
                            )
                        ),
                        Pair(
                            R.drawable.selected_hands,
                            BodyPart(
                                id = BodyPartID.HANDS, name = R.string.body_part_hands
                            )
                        ),
                        Pair(
                            R.drawable.selected_hips,
                            BodyPart(
                                id = BodyPartID.HIP, name = R.string.body_part_hips
                            )
                        ),
                        Pair(
                            R.drawable.selected_legs,
                            BodyPart(
                                id = BodyPartID.LEGS, name = R.string.body_part_legs
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
                    subtitle1 = R.string.form_question_frequency_subtitle1,
                    question = R.string.form_question_frequency,
                    subtitle2 = R.string.form_question_frequency_subtitle2,
                    options = listOf(
                        R.string.frequency_never,
                        R.string.frequency_once,
                        R.string.frequency_twice,
                        R.string.frequency_three,
                        R.string.frequency_four
                    )
                )
            )
        ),
        FormUiModel(
            addNextBtn = true,
            questions = listOf(
                QuestionUiModel.CustomGenderSingleChoice(
                    id = QuestionID.GENDER,
                    question = R.string.form_question_gender,
                    subtitle2 = R.string.form_question_gender_subtitle,
                    male = Pair(R.drawable.man, R.string.gender_male),
                    female = Pair(R.drawable.woman, R.string.gender_female),
                    preferNotToSay = R.string.gender_prefer_not_to_say,
                    // selected = R.string.gender_prefer_not_to_say // Optional default selection
                    // Or remove selected to have no selection by default?
                    // Previous one had "Prefer not to say".
                    // I'll keep it as selected.
                    selected = R.string.gender_prefer_not_to_say
                )
            )
        ),
        FormUiModel(
            questions = listOf(
                QuestionUiModel.SingleChoice(
                    id = QuestionID.AGE,
                    subtitle1 = R.string.form_question_age_subtitle1,
                    subtitle2 = R.string.form_question_age_subtitle2,
                    question = R.string.agre_question,
                    options = listOf(
                        R.string.age_range_18_35,
                        R.string.age_range_36_45,
                        R.string.age_range_46_55,
                        R.string.age_range_56_69,
                        R.string.age_range_over_70
                    )
                )
            )
        ),
        FormUiModel(
            addNextBtn = true,
            questions = listOf(
                QuestionUiModel.WorkDays(
                    id = QuestionID.WORK_DAYS,
                    question = R.string.form_question_work_days,
                    subtitle2 = R.string.form_question_work_days_subtitle,
                    selected = listOf("Mon", "Tue", "Wed", "Thu", "Fri"),
                    days = listOf(
                        Pair(R.string.weekday_mon, "Mon"),
                        Pair(R.string.weekday_tue, "Tue"),
                        Pair(R.string.weekday_wed, "Wed"),
                        Pair(R.string.weekday_thu, "Thu"),
                        Pair(R.string.weekday_fri, "Fri"),
                        Pair(R.string.weekday_sat, "Sat"),
                        Pair(R.string.weekday_sun, "Sun")
                    )
                ),
                QuestionUiModel.TimeRange(
                    id = QuestionID.WORK_HOURS,
                    question = R.string.form_question_work_hours
                )
            )
        ),
        FormUiModel(
            addNextBtn = true,
            questions = listOf(
                QuestionUiModel.ImageSingleChoice(
                    id = QuestionID.MAIN_POSTURE,
                    question = R.string.form_question_posture,
                    subtitle2 = R.string.form_question_posture_subtitle,
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
                    question = R.string.form_question_notification,
                    subtitle2 = R.string.form_question_notification_subtitle,
                )
            )
        )
    )
}
