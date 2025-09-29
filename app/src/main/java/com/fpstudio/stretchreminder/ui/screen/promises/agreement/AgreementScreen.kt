package com.fpstudio.stretchreminder.ui.screen.promises.agreement

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.ui.composable.button.StretchButton
import com.fpstudio.stretchreminder.ui.composable.button.StretchButtonUiModel
import com.fpstudio.stretchreminder.ui.theme.Green_quaternary
import com.fpstudio.stretchreminder.util.Constants.SPACE

@Composable
fun AgreementScreen(
    state: AgreementUiModel,
    onNoClick: () -> Unit,
    onYesClick: () -> Unit,
) {

    val gradient = Brush.verticalGradient(
        startY = 100F,
        colors = listOf(Color.Transparent, Color.Black),
        tileMode = TileMode.Decal
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = state.backgroundImg),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp)
                .align(Alignment.BottomCenter)
                .background(gradient)
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = state.title,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth(),
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                StretchButton(
                    modifier = Modifier.height(48.dp),
                    state = state.noButton,
                    onClick = onNoClick
                )
                StretchButton(
                    modifier = Modifier.height(48.dp).weight(1f),
                    state = state.yesButton,
                    onClick = onYesClick
                )
            }
        }
    }
}

@Preview
@Composable
fun AgreementScreenPreview() {
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
    AgreementScreen(
        state = AgreementUiModel(
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
        ),
        onNoClick = {},
        onYesClick = {},
    )
}