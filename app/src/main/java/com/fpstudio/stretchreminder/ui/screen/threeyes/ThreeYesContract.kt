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
import com.fpstudio.stretchreminder.ui.screen.promises.agreement.AgreementTextPart
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

fun getBaseThreeYesList(isFemale: Boolean = false): List<AgreementUiModel> = listOf(
    getScreen1(isFemale),
    getScreen2(isFemale),
    getScreen3(isFemale)
)


private fun getScreen1(isFemale: Boolean): AgreementUiModel {
    val titleParts = listOf(
        AgreementTextPart(R.string.agreement_screen_1_part_1, isHighlight = false),
        AgreementTextPart(R.string.agreement_screen_1_part_2, isHighlight = true),
        AgreementTextPart(R.string.agreement_screen_1_part_3, isHighlight = false),
        AgreementTextPart(R.string.agreement_screen_1_part_4, isHighlight = true)
    )
    return AgreementUiModel(
        titleParts = titleParts,
        backgroundImg = if (isFemale) R.drawable.female1 else R.drawable.male1,
        noButton = StretchButtonUiModel.Outline(
            text = R.string.common_button_no,
            shape = RoundedCornerShape(12.dp),
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        yesButton = StretchButtonUiModel.Default(
            text = R.string.common_button_yes,
            shape = RoundedCornerShape(12.dp),
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        ),
    )
}

private fun getScreen2(isFemale: Boolean): AgreementUiModel {
    val titleParts = listOf(
        AgreementTextPart(R.string.agreement_screen_2_part_1, isHighlight = false),
        AgreementTextPart(R.string.agreement_screen_2_part_2, isHighlight = true),
        AgreementTextPart(R.string.agreement_screen_2_part_3, isHighlight = false)
    )
    return AgreementUiModel(
        titleParts = titleParts,
        backgroundImg = if (isFemale) R.drawable.female2 else R.drawable.male2,
        noButton = StretchButtonUiModel.Outline(
            text = R.string.common_button_no,
            shape = RoundedCornerShape(12.dp),
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        yesButton = StretchButtonUiModel.Default(
            text = R.string.common_button_yes,
            shape = RoundedCornerShape(12.dp),
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        ),
    )
}

private fun getScreen3(isFemale: Boolean): AgreementUiModel {
    val titleParts = listOf(
        AgreementTextPart(R.string.agreement_screen_3_part_1, isHighlight = false),
        AgreementTextPart(R.string.agreement_screen_3_part_2, isHighlight = true),
        AgreementTextPart(R.string.agreement_screen_3_part_3, isHighlight = false),
        AgreementTextPart(R.string.agreement_screen_3_part_4, isHighlight = true)
    )
    return AgreementUiModel(
        titleParts = titleParts,
        backgroundImg = if (isFemale) R.drawable.female3 else R.drawable.male3,
        noButton = StretchButtonUiModel.Outline(
            text = R.string.common_button_no,
            shape = RoundedCornerShape(12.dp),
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        yesButton = StretchButtonUiModel.Default(
            text = R.string.common_button_yes,
            shape = RoundedCornerShape(12.dp),
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        ),
    )
}