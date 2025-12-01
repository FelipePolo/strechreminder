package com.fpstudio.stretchreminder.ui.composable.question.inputText

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.ImeAction
import com.fpstudio.stretchreminder.ui.composable.question.common.QuestionTitle
import com.fpstudio.stretchreminder.ui.composable.question.QuestionUiModel
import com.fpstudio.stretchreminder.ui.composable.question.QuestionSelectionUiModel
import com.fpstudio.stretchreminder.ui.composable.question.QuestionErrorType
import com.fpstudio.stretchreminder.ui.composable.question.QuestionID
import com.fpstudio.stretchreminder.ui.composable.vibrateOneShot
import com.fpstudio.stretchreminder.ui.screen.form.FormScreenContract.SideEffect
import com.fpstudio.stretchreminder.ui.theme.Gray
import com.fpstudio.stretchreminder.ui.theme.Gray5
import com.fpstudio.stretchreminder.ui.theme.Green_tertiary
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InputTextQuestion(
    model: QuestionUiModel.InputText,
    sideEffect: Flow<SideEffect>,
    onDone: () -> Unit,
    onSelect: (QuestionSelectionUiModel.StringSelectionUiModel) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column {
        Spacer(modifier = Modifier.height(12.dp))
        QuestionTitle(model)
        Spacer(modifier = Modifier.height(18.dp))

        val offsetX = remember { Animatable(0f) }
        var unfocusedBorderColor by remember { mutableStateOf(Gray) }
        var focusedBorderColor by remember { mutableStateOf(Green_tertiary) }

        val context = LocalContext.current
        LaunchedEffect(true) {
            sideEffect.collectLatest { effect ->
                when(effect) {
                    is SideEffect.ShowErrorOnQuestion -> {
                        if (effect.errorType == QuestionErrorType.INPUT_TEXT_INCOMPLETE) {
                            vibrateOneShot(context)
                            unfocusedBorderColor = Color.Red
                            focusedBorderColor = Color.Red
                            repeat(3) {
                                offsetX.animateTo(
                                    targetValue = 5f,
                                    animationSpec = tween(
                                        durationMillis = 50,
                                        easing = FastOutSlowInEasing
                                    )
                                )
                                offsetX.animateTo(
                                    targetValue = -5f,
                                    animationSpec = tween(
                                        durationMillis = 50,
                                        easing = FastOutSlowInEasing
                                    )
                                )
                            }
                            offsetX.animateTo(0f)

                            delay(300)
                            unfocusedBorderColor = Gray
                            focusedBorderColor = Green_tertiary
                        }
                    }
                    else -> {}
                }
            }
        }
        
        OutlinedTextField(
            value = model.selected,
            onValueChange = {
                onSelect(QuestionSelectionUiModel.StringSelectionUiModel(it))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 80.dp)
                .offset(x = offsetX.value.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Gray5,
                focusedContainerColor = Color.White,
                unfocusedBorderColor = unfocusedBorderColor,
                focusedBorderColor = focusedBorderColor,
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    onDone()
                }
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InputTextQuestionPreview() {
    InputTextQuestion(
        model = QuestionUiModel.InputText(
            id = QuestionID.NAME,
            question = "What should we call you?",
            subtitle1 = "First thing first",
            subtitle2 = "This will be used throughout the app",
            selected = ""
        ),
        sideEffect = emptyFlow(),
        onSelect = {},
        onDone = {}
    )
}