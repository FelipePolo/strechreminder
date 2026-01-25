package com.fpstudio.stretchreminder.util.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.fpstudio.stretchreminder.BuildConfig
import com.fpstudio.stretchreminder.data.model.User
import com.fpstudio.stretchreminder.receiver.ReminderReceiver
import java.util.Calendar
import java.util.Locale

class NotificationScheduler(private val context: Context) {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun scheduleNotifications(user: User) {
        // First, cancel all existing alarms to avoid duplicates
        cancelAllNotifications()

        if (!user.notificationPermission) {
            Log.d(TAG, "Notifications disabled by user permission.")
            return
        }

        if (user.workDays.isEmpty()) {
            Log.d(TAG, "No work days selected.")
            return
        }

        // SANDBOX TESTING: Schedule an immediate notification for testing purposes
        if (BuildConfig.FLAVOR == "sandbox") {
            Log.d(TAG, "Sandbox mode detected: Scheduling test notification in 10 seconds.")
            scheduleTestNotification()
        }

        val workDays = user.workDays.mapNotNull { parseDayOfWeek(it) }
        if (workDays.isEmpty()) return

        // Extract hour and minute from start/end times
        val (startHour, startMinute) = getHourMinute(user.startTime)
        val (endHour, endMinute) = getHourMinute(user.endTime)

        // Calculate work duration in minutes
        val startTotalMinutes = startHour * 60 + startMinute
        val endTotalMinutes = endHour * 60 + endMinute
        val durationMinutes = endTotalMinutes - startTotalMinutes

        if (durationMinutes <= 0) {
             Log.d(TAG, "Invalid work duration.")
             return
        }

        Log.d(TAG, "Scheduling for user: Start=$startHour:$startMinute, End=$endHour:$endMinute, Days=$workDays")

        // Max 4 notifications, every 2 hours (120 minutes)
        val maxNotifications = 4
        val intervalMinutes = 120

        for (dayOfWeek in workDays) {
            for (i in 1..maxNotifications) {
                val notificationTimeMinutes = startTotalMinutes + (i * intervalMinutes)

                // Check if this time is still within work hours (or exactly at end time? Usually before end.)
                // Let's say strictly less than end time to avoid notification right when leaving.
                // Or <= end time. The user example: 8-4 (8h). 10, 12, 2, 4. So <= end time is okay.
                if (notificationTimeMinutes > endTotalMinutes) {
                    break
                }

                val notifHour = notificationTimeMinutes / 60
                val notifMinute = notificationTimeMinutes % 60

                scheduleWeeklyAlarm(dayOfWeek, notifHour, notifMinute, i)
            }
        }
    }

    private fun scheduleWeeklyAlarm(dayOfWeek: Int, hour: Int, minute: Int, index: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_WEEK, dayOfWeek)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        // If the day is in the past for this week, AlarmManager might fire immediately if we don't handle it
        // But for setRepeating with RTC_WAKEUP, it generally schedules for the next occurrence.
        // Actually, logic for setRepeating: if time is past, it triggers immediately. 
        // We should ensure we schedule for the *next* occurrence.
        if (calendar.timeInMillis < System.currentTimeMillis()) {
            calendar.add(Calendar.WEEK_OF_YEAR, 1)
        }

        val requestCode = (dayOfWeek * 100) + index
        val intent = Intent(context, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        try {
             // Use setRepeating for weekly alarms
             alarmManager.setRepeating(
                 AlarmManager.RTC_WAKEUP,
                 calendar.timeInMillis,
                 AlarmManager.INTERVAL_DAY * 7,
                 pendingIntent
             )
             Log.d(TAG, "Scheduled alarm for Day=$dayOfWeek Time=$hour:$minute ID=$requestCode")
        } catch (e: SecurityException) {
            Log.e(TAG, "Permission error scheduling alarm: ${e.message}")
        }
    }

    fun cancelAllNotifications() {
        // Cancel for all possible days (1..7) and max indices (1..4)
        for (day in Calendar.SUNDAY..Calendar.SATURDAY) {
            for (i in 1..4) {
                val requestCode = (day * 100) + i
                val intent = Intent(context, ReminderReceiver::class.java)
                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    requestCode,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_NO_CREATE
                )
                
                if (pendingIntent != null) {
                    alarmManager.cancel(pendingIntent)
                    pendingIntent.cancel()
                }
            }
        }
        Log.d(TAG, "Cancelled all notifications")
    }



    private fun scheduleTestNotification() {
        val calendar = Calendar.getInstance().apply {
            add(Calendar.SECOND, 10)
        }

        val intent = Intent(context, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            9999, // Unique ID for test notification
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        try {
            // Use set() which acts as setExact() on older versions or inexact on newer, sufficient for "10s test"
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
            Log.d(TAG, "Scheduled TEST alarm for 10 seconds from now")
        } catch (e: SecurityException) {
            Log.e(TAG, "Permission error scheduling test alarm: ${e.message}")
        }
    }

    private fun getHourMinute(timestamp: Long): Pair<Int, Int> {
        if (timestamp == 0L) return Pair(9, 0) // Default
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        return Pair(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE))
    }

    private fun parseDayOfWeek(dayStr: String): Int? {
        return when (dayStr.lowercase(Locale.ROOT)) {
            "mon", "monday", "lun", "lunes" -> Calendar.MONDAY
            "tue", "tuesday", "mar", "martes" -> Calendar.TUESDAY
            "wed", "wednesday", "mie", "miercoles", "mié" -> Calendar.WEDNESDAY
            "thu", "thursday", "jue", "jueves" -> Calendar.THURSDAY
            "fri", "friday", "vie", "viernes" -> Calendar.FRIDAY
            "sat", "saturday", "sab", "sabado", "sáb" -> Calendar.SATURDAY
            "sun", "sunday", "dom", "domingo" -> Calendar.SUNDAY
            else -> null
        }
    }

    companion object {
        private const val TAG = "NotificationScheduler"
    }
}
