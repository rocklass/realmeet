package com.rocklass.realmeet.core.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import androidx.core.app.NotificationCompat
import javax.inject.Inject

class PushNotificationManager @Inject constructor(
    private val context: Context,
) {
    fun createNotificationChannel() {
        println("### Creating notification channel")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "realmeet"
            val channelName = "RealMeet"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel = NotificationChannel(channelId, channelName, importance).apply {
                description = "RealMeet notifications"
            }

            val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)

            println("### Notification channel created")
        }
    }

    fun sendNotification(title: String, message: String) {
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)?.apply {
            putExtra("notification_type", NotificationType.LAUNCH_REAL_MEET)
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE,
        )

        val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "realmeet"
        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()

        notificationManager.notify(1, notification)
    }
}