package com.fpstudio.stretchreminder.ui.screen.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fpstudio.stretchreminder.domain.model.SubscriptionInfo
import com.fpstudio.stretchreminder.domain.model.PlanType
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PremiumMemberCard(
    subscriptionInfo: SubscriptionInfo,
    onManageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF1A2332), // Dark navy
                        Color(0xFF2D4A5A)  // Teal-ish dark
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(20.dp)
    ) {
        // Premium icon in top right with circular background and border
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(80.dp)
                .offset(x = 15.dp, y = (-15).dp)
                .background(
                    color = Color(0xFF2D4A5A).copy(alpha = 0.3f),
                    shape = androidx.compose.foundation.shape.CircleShape
                )
                .padding(2.dp)
                .background(
                    color = Color.Transparent,
                    shape = androidx.compose.foundation.shape.CircleShape
                )
                .then(
                    Modifier.drawBehind {
                        drawCircle(
                            color = Color(0xFF3A5A6A),
                            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2.dp.toPx())
                        )
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = androidx.compose.ui.res.painterResource(id = com.fpstudio.stretchreminder.R.drawable.ic_premium),
                contentDescription = "Premium",
                tint = Color(0xFF00D9A3),
                modifier = Modifier.size(40.dp)
            )
        }
        
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "CURRENT PLAN",
                    color = Color(0xFFB0B0B0),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.sp
                )
                
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF00D9A3).copy(alpha = 0.2f),
                    modifier = Modifier.padding(start = 4.dp)
                ) {
                    Text(
                        text = "PRO",
                        color = Color(0xFF00D9A3),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Title
            Text(
                text = "Premium\nMember",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 38.sp
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Features
            PremiumFeature("Ads - Free experience")
            Spacer(modifier = Modifier.height(12.dp))
            PremiumFeature("Unlimited Routines")
            Spacer(modifier = Modifier.height(12.dp))
            PremiumFeature("Access all content")
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Divider
            Divider(
                color = Color.White.copy(alpha = 0.2f),
                thickness = 1.dp
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Renewal info and button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Renews on",
                        color = Color(0xFFB0B0B0),
                        fontSize = 14.sp
                    )
                    Text(
                        text = formatRenewalDate(subscriptionInfo.renewalDate),
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                
//                Button(
//                    onClick = onManageClick,
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color(0xFF3A4A5A),
//                        contentColor = Color.White
//                    ),
//                    shape = RoundedCornerShape(24.dp),
//                    modifier = Modifier.height(48.dp)
//                ) {
//                    Text(
//                        text = "Manage",
//                        fontSize = 16.sp,
//                        fontWeight = FontWeight.SemiBold,
//                        modifier = Modifier.padding(horizontal = 16.dp)
//                    )
//                }
            }
        }
    }
}

@Composable
private fun PremiumFeature(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = Color(0xFF00D9A3),
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = text,
            color = Color.White.copy(alpha = 0.9f),
            fontSize = 16.sp
        )
    }
}

private fun formatRenewalDate(timestamp: Long): String {
    val date = Date(timestamp)
    val format = SimpleDateFormat("MMM dd, yyyy", Locale.US)
    return format.format(date)
}
