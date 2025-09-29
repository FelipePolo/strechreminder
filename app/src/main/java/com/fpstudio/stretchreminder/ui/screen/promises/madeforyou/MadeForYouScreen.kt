package com.fpstudio.stretchreminder.ui.screen.promises.madeforyou

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fpstudio.stretchreminder.ui.composable.button.StretchButton
import com.fpstudio.stretchreminder.ui.theme.Green_gradient_1
import com.fpstudio.stretchreminder.ui.theme.Green_gradient_2
import com.fpstudio.stretchreminder.ui.theme.StretchReminderTheme

@Composable
fun MadeForYouScreen(
    modifier: Modifier = Modifier,
    uiState: MadeForYouUiModel = MadeForYouUiModel(),
    onBackClick: () -> Unit = {},
    onContinueClick: () -> Unit = {}
) {
    val gradient = Brush.horizontalGradient(
        startX = 1F,
        colors = listOf(Green_gradient_1, Green_gradient_2),
        tileMode = TileMode.Decal
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = gradient
            )
            .padding(top = 40.dp, bottom = 24.dp)
    ) {
        // Back Button
        if (uiState.showBackButton) {
            StretchButton(
                modifier = Modifier
                    .size(40.dp)
                    .rotate(90f)
                    .align(Alignment.TopStart),
                state = uiState.backButton,
                onClick = onBackClick
            )
        }

        // Main Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            // Feature Cards
            FeatureCard(
                icon = uiState.card1.icon,
                title = uiState.card1.title,
                description = uiState.card1.description,
                gradient = gradient,
                modifier = Modifier.rotate(-3f)
            )

            Spacer(modifier = Modifier.height(30.dp))

            FeatureCard(
                icon = uiState.card2.icon,
                title = uiState.card2.title,
                description = uiState.card2.description,
                gradient = gradient,
                modifier = Modifier
                    .rotate(3f)
                    .offset(x = 16.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            FeatureCard(
                icon = uiState.card3.icon,
                title = uiState.card3.title,
                description = uiState.card3.description,
                gradient = gradient,
                modifier = Modifier.rotate(-3f)
            )

            Spacer(modifier = Modifier.weight(1f))

            // Made For You Text
            Text(
                text = uiState.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = uiState.subtitle,
                fontSize = 16.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Continue Button
            StretchButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                state = uiState.nextButton,
                onClick = onContinueClick
            )
        }
    }
}

@Composable
fun FeatureCard(
    icon: String,
    title: String,
    description: String,
    gradient: Brush,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Icon
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(gradient),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = icon,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.padding(horizontal = 4.dp))

                Text(
                    text = title,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 21.sp,
                    color = Color.Black
                )

            }

            // Text Content
            Text(
                text = description,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MadeForYouScreenPreview() {
    StretchReminderTheme {
        MadeForYouScreen(uiState = MadeForYouUiModel())
    }
}