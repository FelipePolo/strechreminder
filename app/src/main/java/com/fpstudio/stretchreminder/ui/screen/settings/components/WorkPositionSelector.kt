package com.fpstudio.stretchreminder.ui.screen.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.ui.screen.settings.contract.SettingsScreenContract.WorkPosition
import com.fpstudio.stretchreminder.ui.theme.Bg_gray
import com.fpstudio.stretchreminder.ui.theme.TurquoiseAccent

@Composable
fun WorkPositionSelector(
    selectedPosition: WorkPosition,
    onPositionSelected: (WorkPosition) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Daily Work Position",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            WorkPosition.values().forEach { position ->
                PositionPill(
                    text = position.displayName,
                    icon = {
                        when (position) {
                            WorkPosition.SITTING ->
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(R.drawable.chair),
                                    tint = Color.Gray,
                                    contentDescription = "controll icon"
                                )

                            WorkPosition.STANDING ->
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(R.drawable.standing),
                                    tint = Color.Gray,
                                    contentDescription = "controll icon"
                                )

                            WorkPosition.ACTIVE ->
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(R.drawable.laying_down),
                                    tint = Color.Gray,
                                    contentDescription = "control icon"
                                )
                        }
                    },
                    isSelected = position == selectedPosition,
                    onClick = { onPositionSelected(position) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun PositionPill(
    text: String,
    icon: @Composable () -> Unit,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(if (isSelected) TurquoiseAccent.copy(alpha = 0.15F) else Bg_gray.copy(alpha = 0.5F))
            .border(
                width = 2.dp,
                color = if (isSelected) TurquoiseAccent else Bg_gray,
                shape = RoundedCornerShape(24.dp)
            )
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp, horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            icon()
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                textAlign = TextAlign.Center
            )
        }
    }
}
