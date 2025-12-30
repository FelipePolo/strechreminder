package com.fpstudio.stretchreminder.ui.screen.premium.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fpstudio.stretchreminder.ui.theme.TurquoiseAccent

data class Benefit(
    val title: String,
    val description: String
)

@Composable
fun BenefitsList(
    modifier: Modifier = Modifier
) {
    val benefits = listOf(
        Benefit(
            title = "Ad-free experience",
            description = "Focus on your routine without distractions"
        ),
        Benefit(
            title = "Unlimited Custom Routines",
            description = "Create and save as many routines as you need"
        ),
        Benefit(
            title = "Access to all content",
            description = "select all the exercises planned for you"
        )
    )
    
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Premium Benefits",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.Black
        )
        
        benefits.forEach { benefit ->
            BenefitItem(
                title = benefit.title,
                description = benefit.description
            )
        }
    }
}

@Composable
private fun BenefitItem(
    title: String,
    description: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Checkmark icon
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
            tint = TurquoiseAccent,
            modifier = Modifier.size(24.dp)
        )
        
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color.Black
            )
            
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}
