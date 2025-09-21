package com.fpstudio.stretchreminder.ui.composable.permision.notification

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import com.fpstudio.stretchreminder.ui.screen.form.FormScreenContract.Intent

@Composable
fun AskNotificationPermission(shouldAsk: Boolean = false, oGranted: () -> Unit, onDenied: () -> Unit) {
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            oGranted()
        } else {
            onDenied()
        }
    }
    if (shouldAsk) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            oGranted()
        }
    }
}

