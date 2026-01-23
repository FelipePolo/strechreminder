package com.fpstudio.stretchreminder.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.res.stringResource
import com.fpstudio.stretchreminder.R

@Composable
fun NoInternetConnectionDialog(
    onRetry: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Cloud Off Icon with background
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(
                            color = Color(0xFFFDB022).copy(alpha = 0.15f),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CloudOff,
                        contentDescription = stringResource(R.string.content_desc_no_internet),
                        tint = Color(0xFFFDB022),
                        modifier = Modifier.size(40.dp)
                    )
                }

                // Title
                Text(
                    text = stringResource(R.string.dialog_no_internet_title),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B),
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp
                )

                // Message
                Text(
                    text = stringResource(R.string.dialog_no_internet_message),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF64748B),
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                )

                // Try Again Button
                Button(
                    onClick = onRetry,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF10B981)
                    )
                ) {
                    Text(
                        text = stringResource(R.string.action_try_again),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}
