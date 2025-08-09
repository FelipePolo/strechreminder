package com.fpstudio.stretchreminder.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.ui.component.VideoPlayer


@Composable
fun IntroScreen() {
    Scaffold { paddingValues ->
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (backgroundVideo, grayOverlay, title, introButton, terms) = createRefs()
            BackgroundVideo(modifier = Modifier.constrainAs(backgroundVideo) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .constrainAs(grayOverlay) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            Text(
                text = stringResource(R.string.intro_promise),
                color = Color.White,
                fontSize = 40.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 40.sp,
                modifier = Modifier
                    .padding(paddingValues = paddingValues)
                    .padding(horizontal = 16.dp)
                    .constrainAs(title) {
                        top.linkTo(parent.top, margin = 32.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            Terms(modifier = Modifier.constrainAs(terms) {
                bottom.linkTo(parent.bottom, margin = 32.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
        }
    }
}

@Composable
fun BackgroundVideo(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val rawUri = "android.resource://${context.packageName}/raw/onboarding"
    VideoPlayer(
        videoSource = rawUri,
        modifier = modifier.fillMaxSize(),
        showControls = false
    )
}

@Composable
fun Terms(modifier: Modifier = Modifier, onClickLink: () -> Unit) {
    val annotatedText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.Gray)) {
            append("textGray ")
        }
        // Parte clickeable
        val start = length
        withStyle(style = SpanStyle(color = Color.White)) {
            append("textWhite ")
        }
        addStringAnnotation(
            tag = "URL",
            annotation = "https://tu-link.com",
            start = start,
            end = length
        )
        withStyle(style = SpanStyle(color = Color.Gray)) {
            append("textGray ")
        }
        withStyle(style = SpanStyle(color = Color.White, fontWeight = FontWeight.Bold)) {
            append("TextWhiteBold")
        }
    }

    ClickableText(
        text = annotatedText,
        modifier = modifier,
        onClick = { offset ->
            annotatedText.getStringAnnotations(tag = "URL", start = offset, end = offset)
                .firstOrNull()?.let {
                    onClickLink()
                }
        }
    )
}