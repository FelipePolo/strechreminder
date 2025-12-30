package com.fpstudio.stretchreminder.ui.composable.permision.notification

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat

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

fun Context.hasNotificationPermission(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    } else {
        // For Android versions below 13, notifications are enabled by default
        true
    }
}
