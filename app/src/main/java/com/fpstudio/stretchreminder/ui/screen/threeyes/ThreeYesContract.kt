package com.fpstudio.stretchreminder.ui.screen.threeyes

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.ui.composable.button.StretchButtonUiModel
import com.fpstudio.stretchreminder.ui.screen.promises.agreement.AgreementUiModel
import com.fpstudio.stretchreminder.ui.theme.Green_quaternary
import com.fpstudio.stretchreminder.util.Constants.SPACE

interface ThreeYesContract {

    data class UiState(
        val page: Int = 0,
        val agreementScreens: List<AgreementUiModel> = getBaseThreeYesList()
    )

    sealed interface Intent {
        object OnContinue : Intent
    }

    sealed interface SideEffect {
        object NavigateNext : SideEffect
    }
}

private fun getBaseThreeYesList(): List<AgreementUiModel> = listOf(
    getScreen1(),
    getScreen2(),
    getScreen3()
)


private fun getScreen1(): AgreementUiModel {
    val annotatedText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.White, fontWeight = FontWeight.Bold)) {
            append("Do you want to feel more ")
        }

        withStyle(style = SpanStyle(color = Green_quaternary, fontWeight = FontWeight.Bold)) {
            append("energized")
        }

        withStyle(style = SpanStyle(color = Color.White, fontWeight = FontWeight.Bold)) {
            append(SPACE + "and ease")
        }

        withStyle(style = SpanStyle(color = Green_quaternary, fontWeight = FontWeight.Bold)) {
            append(SPACE + "Pain?")
        }
    }
    return AgreementUiModel(
        title = annotatedText,
        backgroundImg = R.drawable.male1,
        noButton = StretchButtonUiModel.Outline(
            text = "No",
            shape = RoundedCornerShape(12.dp),
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        yesButton = StretchButtonUiModel.Default(
            text = "Yes",
            shape = RoundedCornerShape(12.dp),
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        ),
    )
}

private fun getScreen2(): AgreementUiModel {
    val annotatedText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.White, fontWeight = FontWeight.Bold)) {
            append("Ready to")
        }

        withStyle(style = SpanStyle(color = Green_quaternary, fontWeight = FontWeight.Bold)) {
            append(SPACE + "improve")
        }

        withStyle(style = SpanStyle(color = Color.White, fontWeight = FontWeight.Bold)) {
            append(SPACE + "your posture at work?")
        }
    }
    return AgreementUiModel(
        title = annotatedText,
        backgroundImg = R.drawable.male2,
        noButton = StretchButtonUiModel.Outline(
            text = "No",
            shape = RoundedCornerShape(12.dp),
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        yesButton = StretchButtonUiModel.Default(
            text = "Yes",
            shape = RoundedCornerShape(12.dp),
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        ),
    )
}

private fun getScreen3(): AgreementUiModel {
    val annotatedText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.White, fontWeight = FontWeight.Bold)) {
            append("Do you want to enjoy a life free of ")
        }

        withStyle(style = SpanStyle(color = Green_quaternary, fontWeight = FontWeight.Bold)) {
            append("tension")
        }

        withStyle(style = SpanStyle(color = Color.White, fontWeight = FontWeight.Bold)) {
            append(SPACE + "and")
        }

        withStyle(style = SpanStyle(color = Green_quaternary, fontWeight = FontWeight.Bold)) {
            append(SPACE + "stress?")
        }
    }
    return AgreementUiModel(
        title = annotatedText,
        backgroundImg = R.drawable.male3,
        noButton = StretchButtonUiModel.Outline(
            text = "No",
            shape = RoundedCornerShape(12.dp),
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        yesButton = StretchButtonUiModel.Default(
            text = "Yes",
            shape = RoundedCornerShape(12.dp),
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        ),
    )
}