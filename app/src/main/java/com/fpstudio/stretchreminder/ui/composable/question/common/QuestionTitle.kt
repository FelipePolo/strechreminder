package com.fpstudio.stretchreminder.ui.composable.question.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fpstudio.stretchreminder.ui.composable.question.QuestionUiModel

@Composable
fun QuestionTitle(
    model: QuestionUiModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        // Optional subtitle1
        if (model.subtitle1.isNotEmpty()) {
            Text(
                text = model.subtitle1,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Text(
            text = model.question,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 24.sp
        )

        // Optional subtitle2
        if (model.subtitle2.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = model.subtitle2,
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
            model = QuestionUiModel.InputText(
                question = "What should we call you?",
                subtitle1 = "First thing first,",
                subtitle2 = "",
                selected = ""
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Preview con subtitle2
        QuestionTitle(
            model = QuestionUiModel.InputText(
                question = "What should we call you?",
                subtitle1 = "",
                subtitle2 = "This will be used throughout the app",
                selected = ""
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Preview con ambos subtítulos
        QuestionTitle(
            model = QuestionUiModel.InputText(
                question = "What should we call you?",
                subtitle1 = "First thing first",
                subtitle2 = "This will be used throughout the app",
                selected = ""
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Preview sin subtítulos
        QuestionTitle(
            model = QuestionUiModel.InputText(
                question = "What should we call you?",
                subtitle1 = "",
                subtitle2 = "",
                selected = ""
            )
        )
    }
}