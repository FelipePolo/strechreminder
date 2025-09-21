package com.fpstudio.stretchreminder.ui.screen.promises.plansuccess

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.ui.composable.button.StretchButton
import com.fpstudio.stretchreminder.ui.composable.triangle.Triangle
import com.fpstudio.stretchreminder.ui.composable.triangle.TriangleOrientation
import com.fpstudio.stretchreminder.ui.theme.Green_gradient_2
import com.fpstudio.stretchreminder.ui.theme.StretchReminderTheme
import com.fpstudio.stretchreminder.ui.theme.text_primary

@Composable
fun PlanSuccessScreen(
    model: PlanSuccessUiModel = PlanSuccessUiModel(),
    onBackClick: () -> Unit = {},
    onContinueClick: () -> Unit = {}
) {
    val gradient = Brush.verticalGradient(
        startY = 0F,
        colors = listOf(Color.White, Green_gradient_2),
        tileMode = TileMode.Decal
    )

    Box(
        modifier = Modifier
            .padding(top = 24.dp)
            .fillMaxSize()
            .background(gradient)
    ) {

        Image(
            painter = painterResource(id = model.imageResId),
            contentDescription = "Success image",
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .align(Alignment.BottomCenter),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Back button
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .align(Alignment.Start)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Message card
            Column {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF00B54D)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = model.title,
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = model.description,
                            color = text_primary,
                            fontSize = 16.sp
                        )
                    }
                }
                Triangle(
                    modifier = Modifier.padding(start = 40.dp),
                    size = 18.dp,
                    color = Color(0xFF00B54D),
                    orientation = TriangleOrientation.BOTTOM
                )
            }


            Spacer(modifier = Modifier.weight(1f))

            // Person image


            Spacer(modifier = Modifier.weight(1f))

            // Continue button
            StretchButton(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                state = model.nextButton,
                onClick = onContinueClick
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlanSuccessScreenPreview() {
    StretchReminderTheme {
        PlanSuccessScreen(
            model = PlanSuccessUiModel(
                title = "Great! Your plan adapts to your level!",
                description = "Plus, we tailor the program to your body's changes to ensure you gain flexibility in an easy, enjoyable way.",
                imageResId = R.drawable.male
            )
        )
    }
}
