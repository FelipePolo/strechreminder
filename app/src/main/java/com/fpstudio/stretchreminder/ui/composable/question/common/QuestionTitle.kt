package com.fpstudio.stretchreminder.ui.composable.question.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fpstudio.stretchreminder.ui.composable.question.QuestionID
import com.fpstudio.stretchreminder.ui.composable.question.QuestionUiModel
import com.fpstudio.stretchreminder.util.Constants

@Composable
fun QuestionTitle(
    model: QuestionUiModel,
    userName: String = Constants.EMPTY,
    modifier: Modifier = Modifier
) {
    val subtitle1: String = model.subtitle1?.let { stringResource(it) } ?: Constants.EMPTY

    Column(modifier = modifier) {
        // Optional subtitle1
        if (subtitle1.isNotEmpty()) {
            Text(
                text = "$subtitle1 $userName",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Text(
            text = model.question?.let { stringResource(it) } ?: Constants.EMPTY,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 24.sp
        )

        // Optional subtitle2
        val subtitle2 = model.subtitle2?.let { stringResource(it) } ?: Constants.EMPTY
        if (subtitle2.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = subtitle2,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionTitlePreview() {
    Column(modifier = Modifier.padding(16.dp)) {
        // Preview con subtitle1
        QuestionTitle(
            userName = "Felipe",
            model = QuestionUiModel.InputText(
                id = QuestionID.NAME,
                question = com.fpstudio.stretchreminder.R.string.form_question_name,
                subtitle1 = com.fpstudio.stretchreminder.R.string.form_question_name_subtitle,
                subtitle2 = null,
                selected = ""
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Preview con subtitle2
        QuestionTitle(
            model = QuestionUiModel.InputText(
                id = QuestionID.NAME,
                question = com.fpstudio.stretchreminder.R.string.form_question_name,
                subtitle1 = null,
                subtitle2 = com.fpstudio.stretchreminder.R.string.form_question_gender_subtitle,
                selected = ""
            )
        )
    }
}