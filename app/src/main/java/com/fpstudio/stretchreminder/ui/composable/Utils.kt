package com.fpstudio.stretchreminder.ui.composable

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

fun vibrateOneShot(context: Context, millis: Long = 100) {
    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager =
            context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    vibrator.vibrate(
        VibrationEffect.createOneShot(
            millis,
            VibrationEffect.DEFAULT_AMPLITUDE
        )
    )
}

fun Long.getTimeString(): String {
    val minutes = (this / 1000) / 60
    val seconds = (this / 1000) % 60
    return "%02d:%02d".format(minutes, seconds)
}