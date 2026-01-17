package com.fpstudio.stretchreminder.ui.screen.settings.components
import com.fpstudio.stretchreminder.R
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.fpstudio.stretchreminder.ui.theme.Bg_gray
import com.fpstudio.stretchreminder.ui.theme.TurquoiseAccent

@Composable
fun ProfileSection(
    displayName: String,
    onNameChanged: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var isEditing by remember { mutableStateOf(false) }
    var editedName by remember { mutableStateOf("") }
    
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    
    // Auto-focus when entering edit mode
    LaunchedEffect(isEditing) {
        if (isEditing) {
            editedName = ""
            focusRequester.requestFocus()
        }
    }
    
    val canSave = editedName.isNotBlank()
    
    fun saveChanges() {
        if (canSave) {
            onNameChanged(editedName.trim())
            isEditing = false
            focusManager.clearFocus()
        }
    }
    
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.profile_title),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.profile_display_name),
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = if (isEditing) editedName else displayName,
                enabled = isEditing,
                singleLine = true,
                onValueChange = { editedName = it },
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester),
                placeholder = if (isEditing) {
                    { Text(text = displayName, color = Color.Gray) }
                } else null,
                textStyle = MaterialTheme.typography.bodyLarge,
                colors = TextFieldDefaults.colors().copy(
                    unfocusedContainerColor = Bg_gray,
                    unfocusedTextColor = Color.Black,
                    focusedContainerColor = Bg_gray,
                    focusedTextColor = Color.Black,
                    disabledContainerColor = Bg_gray,
                    disabledTextColor = Color.Black,
                    disabledPlaceholderColor = Color.Black,
                    // Remove underline
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                },
                shape = RoundedCornerShape(16.dp),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        saveChanges()
                    }
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Edit/Done button
            if (!isEditing) {
                // Edit button (text only, no background)
                TextButton(
                    onClick = { isEditing = true },
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.action_edit),
                        color = TurquoiseAccent,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            } else {
                // Done button (with background)
                Button(
                    onClick = { saveChanges() },
                    enabled = canSave,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = TurquoiseAccent,
                        disabledContainerColor = Color.LightGray,
                        contentColor = Color.White,
                        disabledContentColor = Color.Gray
                    ),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.action_done),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
