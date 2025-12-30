package com.fpstudio.stretchreminder.ui.screen.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.ui.screen.settings.components.*
import com.fpstudio.stretchreminder.ui.screen.settings.contract.SettingsScreenContract.Intent
import com.fpstudio.stretchreminder.ui.screen.settings.contract.SettingsScreenContract.SideEffect
import com.fpstudio.stretchreminder.ui.screen.settings.contract.SettingsScreenContract.UiState
import com.fpstudio.stretchreminder.ui.screen.settings.contract.SettingsScreenContract.RoutinePreferencesState
import com.fpstudio.stretchreminder.ui.theme.TurquoiseAccent
import com.fpstudio.stretchreminder.ui.composable.permision.notification.createNotificationPermissionLauncher
import com.fpstudio.stretchreminder.ui.composable.permision.notification.askPermission
import com.fpstudio.stretchreminder.ui.composable.permision.notification.hasNotificationPermission
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsScreenViewModel = koinViewModel(),
    onNavigateBack: () -> Unit = {},
    onNavigateToPremium: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                SideEffect.NavigateBack -> onNavigateBack()

                SideEffect.NavigateToPremium -> {
                    onNavigateToPremium()
                }

                SideEffect.NavigateToRateApp -> {
                    // TODO: Open Play Store
                }

                SideEffect.NavigateToFeedback -> {
                    // TODO: Open feedback form
                }

                SideEffect.ShowSaveSuccess -> {
                    // TODO: Show success message
                }
            }
        }
    }

    SettingsContent(
        uiState = uiState,
        onIntent = viewModel::handleIntent,
        onNavigateBack = onNavigateBack,
        viewModel = viewModel
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsContent(
    uiState: UiState,
    onIntent: (Intent) -> Unit,
    onNavigateBack: () -> Unit = {},
    viewModel: SettingsScreenViewModel
) {
    // Use ViewModel's change detection
    val hasUnsavedChanges by remember {
        derivedStateOf { viewModel.hasUnsavedChanges() }
    }

    val context = LocalContext.current
    
    // Notification permission launcher
    val notificationPermissionLauncher = createNotificationPermissionLauncher(
        oGranted = {
            // Permission granted, enable notifications
            onIntent(Intent.ToggleNotifications(true))
        },
        onDenied = {
            // Permission denied, keep notifications disabled
            onIntent(Intent.ToggleNotifications(false))
        }
    )
    
    var showStartTimePicker by remember { mutableStateOf(false) }
    var showEndTimePicker by remember { mutableStateOf(false) }
    var showUnsavedChangesDialog by remember { mutableStateOf(false) }
    Scaffold(
        containerColor = Color(0xFFF5F5F5),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Settings",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (hasUnsavedChanges) {
                            showUnsavedChangesDialog = true
                        } else {
                            onNavigateBack()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF5F5F5)
                )
            )
        },
        floatingActionButton = {
            Button(
                onClick = { onIntent(Intent.SaveChanges) },
                modifier = Modifier
                    .fillMaxWidth(0.9f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = TurquoiseAccent
                ),
                shape = RoundedCornerShape(24.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 12.dp,
                    pressedElevation = 16.dp
                )
            ) {
                Text(
                    text = "Save Changes",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Plan Card
                PlanCard(
                    planType = uiState.plan.planType,
                    adsEnabled = uiState.plan.adsEnabled,
                    onUpgradeClick = { onIntent(Intent.UpgradeToPremium) }
                )

                // Profile Section
                ProfileSection(
                    displayName = uiState.profile.displayName,
                    onNameChanged = { newName -> onIntent(Intent.UpdateDisplayName(newName)) }
                )

                // Routine Preferences
                RoutinePreferencesSection(
                    preferences = uiState.routinePreferences,
                    onIntent = onIntent,
                    onStartTimeClick = { showStartTimePicker = true },
                    onEndTimeClick = { showEndTimePicker = true }
                )

                // App Settings
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    SettingsItem(
                        iconRes = R.drawable.ic_bell,
                        iconBackgroundColor = Color(0xFFE3F2FD),
                        iconTint = Color(0xFF2196F3),
                        title = "Notifications",
                        hasToggle = true,
                        toggleValue = uiState.appSettings.notificationsEnabled,
                        onToggleChanged = { enabled ->
                            if (enabled) {
                                // Check if we already have permission
                                if (context.hasNotificationPermission()) {
                                    // Already have permission, just enable
                                    onIntent(Intent.ToggleNotifications(true))
                                } else {
                                    // Need to request permission
                                    notificationPermissionLauncher.askPermission {
                                        onIntent(Intent.ToggleNotifications(true))
                                    }
                                }
                            } else {
                                // Disabling notifications, no permission needed
                                onIntent(Intent.ToggleNotifications(false))
                            }
                        }
                    )

                    Divider(color = Color(0xFFF5F5F5), thickness = 1.dp)

                    SettingsItem(
                        iconRes = R.drawable.ic_premium,
                        iconBackgroundColor = Color(0xFFFFF9C4),
                        iconTint = Color(0xFFFFA000),
                        title = "Rate on Google Play",
                        hasArrow = true,
                        onClick = { onIntent(Intent.RateApp) }
                    )

                    Divider(color = Color(0xFFF5F5F5), thickness = 1.dp)

                    SettingsItem(
                        iconRes = R.drawable.ic_comment,
                        iconBackgroundColor = Color(0xFFF3E5F5),
                        iconTint = Color(0xFF9C27B0),
                        title = "Send Feedback",
                        hasArrow = true,
                        onClick = { onIntent(Intent.SendFeedback) }
                    )
                }
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }

    // Unsaved changes dialog
    if (showUnsavedChangesDialog) {
        UnsavedChangesDialog(
            onDismiss = { showUnsavedChangesDialog = false },
            onSaveAndExit = {
                onIntent(Intent.SaveChanges)
                showUnsavedChangesDialog = false
                onNavigateBack()
            },
            onExitWithoutSaving = {
                showUnsavedChangesDialog = false
                onNavigateBack()
            }
        )
    }

    // Time picker dialogs
    if (showStartTimePicker) {
        TimePickerDialog(
            initialTime = uiState.routinePreferences.startTime,
            onDismiss = { showStartTimePicker = false },
            onTimeSelected = { time ->
                onIntent(Intent.UpdateStartTime(time))
            }
        )
    }

    if (showEndTimePicker) {
        TimePickerDialog(
            initialTime = uiState.routinePreferences.endTime,
            onDismiss = { showEndTimePicker = false },
            onTimeSelected = { time ->
                onIntent(Intent.UpdateEndTime(time))
            }
        )
    }
}

@Composable
private fun RoutinePreferencesSection(
    preferences: RoutinePreferencesState,
    onIntent: (Intent) -> Unit,
    onStartTimeClick: () -> Unit,
    onEndTimeClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(R.drawable.control),
                tint = TurquoiseAccent,
                contentDescription = "controll icon"
            )
            Text(
                text = "Routine Preferences",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        WorkPositionSelector(
            selectedPosition = preferences.workPosition,
            onPositionSelected = { onIntent(Intent.SelectWorkPosition(it)) }
        )

        FocusAreaSelector(
            selectedAreas = preferences.focusAreas,
            onAreaToggled = { onIntent(Intent.ToggleFocusArea(it)) }
        )

        WorkdaySelector(
            selectedDays = preferences.workdays,
            onDayToggled = { onIntent(Intent.ToggleWorkday(it)) }
        )

        TimePickerRow(
            startTime = preferences.startTime,
            endTime = preferences.endTime,
            onStartTimeClick = onStartTimeClick,
            onEndTimeClick = onEndTimeClick
        )
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    // Preview without ViewModel - change detection won't work in preview
    MaterialTheme {
        Scaffold(
            containerColor = Color(0xFFF5F5F5)
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Text("Settings Preview")
            }
        }
    }
}
