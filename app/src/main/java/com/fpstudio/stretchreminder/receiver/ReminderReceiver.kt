package com.fpstudio.stretchreminder.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.MainActivity

class ReminderReceiver : BroadcastReceiver() {

    companion object {
        const val CHANNEL_ID = "stretch_reminders_channel"
        const val CHANNEL_NAME = "Stretch Reminders"
        const val NOTIFICATION_ID = 1001
    }

    override fun onReceive(context: Context, intent: Intent) {
        // Create channel if necessary
        createNotificationChannel(context)

        // Create intent to open app when notification is clicked
        val contentIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 
            0, 
            contentIntent, 
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Build notification
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            // Use a small icon from resources - assuming one exists, otherwise use a generic system one or the app icon if possible
            .setSmallIcon(R.mipmap.ic_launcher) // Fallback to launcher if specific notification icon missing
            .setContentTitle("Time to Stretch!")
            .setContentText("Take a break and stretch to feel better.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        // Show notification
        try {
            // Check permission? SecurityException might occur on Android 13+ if permission not granted.
            // However, this is a receiver, we assume permission was checked before scheduling.
             with(NotificationManagerCompat.from(context)) {
                if (androidx.core.content.ContextCompat.checkSelfPermission(
                        context,
                        android.Manifest.permission.POST_NOTIFICATIONS
                    ) == android.content.pm.PackageManager.PERMISSION_GRANTED
                ) {
                    notify(NOTIFICATION_ID, builder.build())
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = "Reminders to stretch during work hours"
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
