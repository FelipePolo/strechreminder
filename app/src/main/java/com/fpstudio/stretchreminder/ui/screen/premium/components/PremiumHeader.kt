package com.fpstudio.stretchreminder.ui.screen.premium.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.ui.theme.TurquoiseAccent

@Composable
fun PremiumHeader(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Premium badge icon with gradient
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF80E5CC),
                            TurquoiseAccent
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Premium",
                tint = Color(0xFFFFD700),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(32.dp)
                    .padding(end = 8.dp, top = 8.dp)
            )

            Icon(
                painter = painterResource(R.drawable.ic_premium),
                contentDescription = "Premium",
                tint = Color(0xFFFFD700),
                modifier = Modifier
                    .size(80.dp)
            )
        }
        
        // Title
        Text(
            text = "Unlock Full Potential",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        
        // Description
        Text(
            text = "Get unlimited access to all premium benefits",
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        )
    }
}
