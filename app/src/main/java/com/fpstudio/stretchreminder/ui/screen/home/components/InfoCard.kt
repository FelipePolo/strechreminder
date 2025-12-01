package com.fpstudio.stretchreminder.ui.screen.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.ui.theme.Green_gradient_1
import com.fpstudio.stretchreminder.ui.theme.Green_gradient_2
import com.fpstudio.stretchreminder.ui.theme.StretchReminderTheme

@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    icon: Int
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp, 16.dp, 16.dp, 16.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(Color.White, Color.Transparent),
                    startX = 0f,
                    endX = 1f,
                ),
            )
            .border(
                2.dp,
                Brush.horizontalGradient(
                    colors = listOf(Green_gradient_1, Green_gradient_2),
                    startX = 0f,
                    endX = 1f,
                ),
                RoundedCornerShape(16.dp, 16.dp, 16.dp, 16.dp),
            )
            .background(Color.White)
            .padding(16.dp)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = Green_gradient_1
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp,
                color = Green_gradient_1,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            color = Green_gradient_1,
            modifier = Modifier.padding(start = 32.dp)
        )
    }
}

@Preview
@Composable
private fun InfoCardPreview() {
    StretchReminderTheme {
        InfoCard(
            title = "Todays stretching",
            value = "2 min",
            icon = R.drawable.clock
        )
    }
}
