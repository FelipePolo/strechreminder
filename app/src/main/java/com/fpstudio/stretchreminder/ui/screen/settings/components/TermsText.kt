package com.fpstudio.stretchreminder.ui.screen.settings.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.ui.theme.TurquoiseAccent
import com.fpstudio.stretchreminder.util.Constants.PRIVATE_POLICY_URL
import com.fpstudio.stretchreminder.util.Constants.SPACE
import com.fpstudio.stretchreminder.util.Constants.TERMS_AND_CONDITIONS_URL

@Composable
fun TermsText(modifier: Modifier = Modifier) {
    val annotatedText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.Gray)) {
            appendLine(stringResource(R.string.intro_private_and_policy))
        }

        withLink(
            LinkAnnotation.Url(
                TERMS_AND_CONDITIONS_URL,
                TextLinkStyles(
                    style = SpanStyle(
                        color = TurquoiseAccent,
                        textDecoration = TextDecoration.Underline
                    )
                ),
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
                TextLinkStyles(
                    style = SpanStyle(
                        color = TurquoiseAccent,
                        textDecoration = TextDecoration.Underline
                    )
                ),
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
