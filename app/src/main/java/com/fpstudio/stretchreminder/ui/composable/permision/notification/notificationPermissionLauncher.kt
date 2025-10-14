package com.fpstudio.stretchreminder.ui.composable.permision.notification

import android.Manifest
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

@Composable
fun createNotificationPermissionLauncher(oGranted: () -> Unit, onDenied: () -> Unit): ManagedActivityResultLauncher<String, Boolean> {
    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            oGranted()
        } else {
            onDenied()
        }
    }
}

fun ManagedActivityResultLauncher<String, Boolean>.askPermission(oGranted: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        launch(Manifest.permission.POST_NOTIFICATIONS)
    } else {
        oGranted()
    }
}
