package com.fpstudio.stretchreminder.ui.screen.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.ui.screen.home.model.HeaderUiState
import com.fpstudio.stretchreminder.ui.theme.Gray3
import com.fpstudio.stretchreminder.ui.theme.StretchReminderTheme
import com.fpstudio.stretchreminder.ui.theme.Green_secondary

@Composable
fun HeaderComponent(
    modifier: Modifier = Modifier,
    uiState: HeaderUiState
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "${uiState.month}  ${uiState.day}".uppercase(),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = Gray3
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = uiState.dayOfWeek,
                fontSize = 38.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Icon(
                painter = painterResource(id = R.drawable.settings),
                contentDescription = "Settings Icon",
                modifier = Modifier.size(40.dp),
                tint = Green_secondary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderComponentPreview() {
    StretchReminderTheme {
        HeaderComponent(
            uiState = HeaderUiState(
                dayOfWeek = "sunday"
            )
        )
    }
}
