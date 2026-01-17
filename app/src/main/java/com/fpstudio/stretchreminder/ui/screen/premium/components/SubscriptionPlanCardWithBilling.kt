package com.fpstudio.stretchreminder.ui.screen.premium.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.revenuecat.purchases.Package
import com.fpstudio.stretchreminder.ui.screen.premium.contract.PremiumScreenContract.SubscriptionPlan
import com.fpstudio.stretchreminder.ui.theme.TurquoiseAccent
import androidx.compose.ui.res.stringResource
import com.fpstudio.stretchreminder.R

@Composable
fun SubscriptionPlanCardWithBilling(
    plan: SubscriptionPlan,
    packageInfo: Package?,
    isSelected: Boolean,
    onPlanSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Extract pricing information from Package
    val formattedPrice = packageInfo?.product?.price?.formatted ?: if (plan == SubscriptionPlan.ANNUAL) "$39.99" else "$7.99"
    val billingPeriod = if (plan == SubscriptionPlan.ANNUAL) "/yr" else "/mo"
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) TurquoiseAccent else Color(0xFFE0E0E0),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onPlanSelected() }
    ) {
        // Best Value Badge
        if (plan == SubscriptionPlan.ANNUAL) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .clip(RoundedCornerShape(topEnd = 16.dp, bottomStart = 16.dp))
                    .background(TurquoiseAccent)
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 8.dp),
                    text = stringResource(R.string.premium_save_badge),
                    fontSize = 6.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = isSelected,
                    onClick = onPlanSelected,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = TurquoiseAccent,
                        unselectedColor = Color.Gray
                    )
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = if (plan == SubscriptionPlan.ANNUAL) stringResource(R.string.premium_plan_annual) else stringResource(R.string.premium_plan_monthly),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )

                    if (plan == SubscriptionPlan.ANNUAL) {
                        Text(
                            text = stringResource(R.string.premium_save_text),
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 12.sp,
                            color = TurquoiseAccent,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = formattedPrice,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.Black
                    )
                    Text(
                        text = billingPeriod,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                }

                if (plan == SubscriptionPlan.ANNUAL) {
                    Text(
                        text = stringResource(R.string.premium_monthly_calculation),
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}
