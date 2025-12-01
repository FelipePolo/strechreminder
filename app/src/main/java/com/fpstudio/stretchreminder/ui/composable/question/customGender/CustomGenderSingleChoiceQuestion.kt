package com.fpstudio.stretchreminder.ui.composable.question.customGender

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.ui.composable.question.QuestionID
import com.fpstudio.stretchreminder.ui.composable.question.QuestionSelectionUiModel
import com.fpstudio.stretchreminder.ui.composable.question.QuestionUiModel
import com.fpstudio.stretchreminder.ui.composable.question.common.QuestionTitle
import com.fpstudio.stretchreminder.ui.theme.Green_secondary
import com.fpstudio.stretchreminder.ui.theme.StretchReminderTheme
import com.fpstudio.stretchreminder.ui.theme.text_primary

@Composable
fun CustomGenderSingleChoiceQuestion(
    model: QuestionUiModel.CustomGenderSingleChoice,
    onSelect: (QuestionSelectionUiModel.StringSelectionUiModel) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // Question title
        Spacer(modifier = Modifier.height(12.dp))
        QuestionTitle(model = model)
        Spacer(modifier = Modifier.height(36.dp))

        // content
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Male option
            GenderOption(
                imageResId = model.male.first,
                label = model.male.second,
                isSelected = model.selected == model.male.second,
                onClick = { onSelect(QuestionSelectionUiModel.StringSelectionUiModel(model.male.second)) }
            )

            // Female option
            GenderOption(
                imageResId = model.female.first,
                label = model.female.second,
                isSelected = model.selected == model.female.second,
                onClick = { onSelect(QuestionSelectionUiModel.StringSelectionUiModel(model.female.second)) }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Prefer not to say option
        Box(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .clip(MaterialTheme.shapes.medium)
                .border(
                    width = 1.dp,
                    color = if (model.selected == model.preferNotToSay)
                        Green_secondary
                    else
                        Color.LightGray,
                    shape = MaterialTheme.shapes.medium
                )
                .background(
                    if (model.selected == model.preferNotToSay)
                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)
                    else
                        Color.Transparent
                )
                .clickable {
                    onSelect(QuestionSelectionUiModel.StringSelectionUiModel(model.preferNotToSay))
                }
                .padding(vertical = 12.dp)
                .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = model.preferNotToSay,
                style = MaterialTheme.typography.bodyLarge,
                color = if (model.selected == model.preferNotToSay)
                    MaterialTheme.colorScheme.onPrimaryContainer
                else
                    MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun GenderOption(
    imageResId: Int,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(
                enabled = true,
                interactionSource = null,
                indication = null
            ) {
                onClick()
            }
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .width(160.dp)
                .height(230.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth()
                    .clip(CircleShape)
                    .border(
                        width = 2.dp,
                        color = if (isSelected) Green_secondary else Color.LightGray,
                        shape = CircleShape
                    )
                    .background(
                        if (isSelected)
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)
                        else
                            text_primary
                    ),
                contentAlignment = Alignment.BottomCenter
            ) {}
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = label,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 10.dp)
                    .clip(RoundedCornerShape(80.dp)),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = if (isSelected)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CustomGenderSingleChoiceQuestionPreview() {
    StretchReminderTheme {
        CustomGenderSingleChoiceQuestion(
            model = QuestionUiModel.CustomGenderSingleChoice(
                id = QuestionID.GENDER,
                question = "What is your gender?",
                subtitle1 = "About you",
                subtitle2 = "Just to personalize your experience a little more.",
                male = Pair(R.drawable.man, "Male"),
                female = Pair(R.drawable.woman, "Female"),
                preferNotToSay = "Prefer not to say",
                selected = "Male"
            ),
            onSelect = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CustomGenderSingleChoiceQuestionFemaleSelectedPreview() {
    StretchReminderTheme {
        CustomGenderSingleChoiceQuestion(
            model = QuestionUiModel.CustomGenderSingleChoice(
                id = QuestionID.GENDER,
                question = "What is your gender?",
                subtitle1 = "About you",
                subtitle2 = "Just to personalize your experience a little more.",
                male = Pair(R.drawable.man, "Male"),
                female = Pair(R.drawable.woman, "Female"),
                preferNotToSay = "Prefer not to say",
                selected = "Female"
            ),
            onSelect = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CustomGenderSingleChoiceQuestionPreferNotToSayPreview() {
    StretchReminderTheme {
        CustomGenderSingleChoiceQuestion(
            model = QuestionUiModel.CustomGenderSingleChoice(
                id = QuestionID.GENDER,
                question = "What is your gender?",
                subtitle1 = "About you",
                subtitle2 = "Just to personalize your experience a little more.",
                male = Pair(R.drawable.man, "Male"),
                female = Pair(R.drawable.woman, "Female"),
                preferNotToSay = "Prefer not to say",
                selected = "Prefer not to say"
            ),
            onSelect = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CustomGenderSingleChoiceQuestionPreferNotToSayPreview2() {
    StretchReminderTheme {
        CustomGenderSingleChoiceQuestion(
            model = QuestionUiModel.CustomGenderSingleChoice(
                id = QuestionID.GENDER,
                question = "What is your gender?",
                subtitle1 = "About you",
                subtitle2 = "Just to personalize your experience a little more.",
                male = Pair(R.drawable.man, "Male"),
                female = Pair(R.drawable.woman, "Female"),
                preferNotToSay = "Prefer not to say",
                selected = "Prefer not to say"
            ),
            onSelect = {}
        )
    }
}
