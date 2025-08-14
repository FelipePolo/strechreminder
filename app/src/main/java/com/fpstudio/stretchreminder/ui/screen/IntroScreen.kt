package com.fpstudio.stretchreminder.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.ui.component.VideoPlayer
import com.fpstudio.stretchreminder.ui.theme.Green
import com.fpstudio.stretchreminder.util.Constants.PRIVATE_POLICY_URL
import com.fpstudio.stretchreminder.util.Constants.SPACE
import com.fpstudio.stretchreminder.util.Constants.TERMS_AND_CONDITIONS_URL


@Composable
fun IntroScreen(
    onClick: () -> Unit
) {
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

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .constrainAs(introButton) {
                        bottom.linkTo(terms.top, margin = 16.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Green),
                shape = RoundedCornerShape(8.dp),
                onClick = onClick
            ) {
                Text(
                    text = stringResource(R.string.intro_button_text),
                    fontSize = 20.sp
                )
            }

            Terms(
                modifier = Modifier.constrainAs(terms) {
                    bottom.linkTo(parent.bottom, margin = 32.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
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
fun Terms(modifier: Modifier = Modifier) {
    val annotatedText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.Gray)) {
            appendLine(stringResource(R.string.intro_private_and_policy))
        }

        withLink(
            LinkAnnotation.Url(
                TERMS_AND_CONDITIONS_URL,
                TextLinkStyles(style = SpanStyle(color = Color.White)),
            )
        ) {
            append(stringResource(R.string.intro_term_services))
        }

        withStyle(style = SpanStyle(color = Color.Gray)) {
            append(SPACE + stringResource(R.string.and) + SPACE)
        }

        withLink(
            LinkAnnotation.Url(
                PRIVATE_POLICY_URL,
                TextLinkStyles(style = SpanStyle(color = Color.White)),
            )
        ) {
            append(stringResource(R.string.intro_privacy))
        }
    }
    Text(
        modifier = modifier,
        text = annotatedText,
        textAlign = TextAlign.Center
    )
}