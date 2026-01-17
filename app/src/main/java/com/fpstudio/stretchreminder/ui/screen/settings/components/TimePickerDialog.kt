package com.fpstudio.stretchreminder.ui.screen.settings.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.fpstudio.stretchreminder.ui.theme.TurquoiseAccent
import kotlinx.coroutines.launch

@SuppressLint("DefaultLocale")
@Composable
fun TimePickerDialog(
    initialTime: String, // Format: "07:00 AM"
    onDismiss: () -> Unit,
    onTimeSelected: (String) -> Unit
) {
    // Parse initial time
    val timeParts = initialTime.split(" ")
    val hourMinute = timeParts[0].split(":")
    val initialHour = hourMinute[0].toIntOrNull() ?: 1
    val initialMinute = hourMinute[1].toIntOrNull() ?: 0
    val initialPeriod = if (timeParts.size > 1) timeParts[1] else "AM"

    var selectedHour by remember { mutableStateOf(initialHour) }
    var selectedMinute by remember { mutableStateOf(initialMinute) }
    var selectedPeriod by remember { mutableStateOf(initialPeriod) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Select Time",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Time picker with circular selection indicator
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Hour picker
                        InfiniteCircularPicker(
                            items = (1..12).toList(),
                            initialItem = selectedHour,
                            onItemSelected = { selectedHour = it },
                            modifier = Modifier.weight(1f)
                        )
                        
                        Text(
                            text = ":",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                        
                        // Minute picker
                        InfiniteCircularPicker(
                            items = (0..59).toList(),
                            initialItem = selectedMinute,
                            onItemSelected = { selectedMinute = it },
                            modifier = Modifier.weight(1f),
                            formatValue = { it.toString().padStart(2, '0') }
                        )
                        
                        Spacer(modifier = Modifier.width(16.dp))
                        
                        // AM/PM picker
                        Column(
                            modifier = Modifier.weight(0.8f),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            PeriodButton(
                                text = "AM",
                                isSelected = selectedPeriod == "AM",
                                onClick = { selectedPeriod = "AM" }
                            )
                            PeriodButton(
                                text = "PM",
                                isSelected = selectedPeriod == "PM",
                                onClick = { selectedPeriod = "PM" }
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        border = null
                    ) {
                        Text("Cancel")
                    }
                    
                    Button(
                        onClick = {
                            val formattedTime = String.format(
                                "%02d:%02d %s",
                                selectedHour,
                                selectedMinute,
                                selectedPeriod
                            )
                            onTimeSelected(formattedTime)
                            onDismiss()
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = TurquoiseAccent
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("OK", color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
private fun InfiniteCircularPicker(
    items: List<Int>,
    initialItem: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    formatValue: (Int) -> String = { it.toString() }
) {
    val itemHeight = 40.dp
    val visibleItemsCount = 5
    val infiniteItems = items.size * 1000 // Create "infinite" list
    
    // Calculate initial index to center the initial item
    // Find the index of the initial item in the list
    val itemIndexInList = items.indexOf(initialItem).takeIf { it >= 0 } ?: 0
    // Start from middle of infinite list, align to list boundary, add item offset, subtract 2 to center
    val middleOfInfiniteList = infiniteItems / 2
    val alignedToListStart = middleOfInfiniteList - (middleOfInfiniteList % items.size)
    val initialIndex = (alignedToListStart + itemIndexInList - 2).coerceAtLeast(0)
    
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = initialIndex)
    val coroutineScope = rememberCoroutineScope()
    
    // Track the center item
    LaunchedEffect(listState.firstVisibleItemIndex, listState.firstVisibleItemScrollOffset) {
        val centerIndex = listState.firstVisibleItemIndex + 2
        val actualItem = items[centerIndex % items.size]
        onItemSelected(actualItem)
    }
    
    Box(
        modifier = modifier
            .height(itemHeight * visibleItemsCount)
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)
        ) {
            items(infiniteItems) { index ->
                val item = items[index % items.size]
                val centerIndex = listState.firstVisibleItemIndex + 2
                val isCenterItem = index == centerIndex
                
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(itemHeight)
                        .clickable {
                            coroutineScope.launch {
                                listState.animateScrollToItem(index - 2)
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = formatValue(item),
                        fontSize = if (isCenterItem) 24.sp else 18.sp,
                        fontWeight = if (isCenterItem) FontWeight.Bold else FontWeight.Normal,
                        color = if (isCenterItem) TurquoiseAccent else Color.Gray,
                        modifier = Modifier.alpha(
                            when {
                                isCenterItem -> 1f
                                index == centerIndex - 1 || index == centerIndex + 1 -> 0.6f
                                else -> 0.3f
                            }
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun PeriodButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) TurquoiseAccent else Color(0xFFF5F5F5))
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) Color.White else Color.Gray
        )
    }
}
