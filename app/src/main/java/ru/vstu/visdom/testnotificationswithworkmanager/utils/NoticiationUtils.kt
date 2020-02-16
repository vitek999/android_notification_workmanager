package ru.vstu.visdom.testnotificationswithworkmanager.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import ru.vstu.visdom.testnotificationswithworkmanager.MainActivity
import ru.vstu.visdom.testnotificationswithworkmanager.R

const val NOTIFICATION_ID = 0

fun NotificationManager.sendNotification(
    messageBody: String,
    contentTitle: String,
    applicationContext: Context,
    channelId: String
) {

    val contentIntent = Intent(applicationContext, MainActivity::class.java)

    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val builder = NotificationCompat.Builder(
        applicationContext,
        channelId
    )

        .setSmallIcon(R.mipmap.ic_launcher)
        .setLargeIcon(
            BitmapFactory.decodeResource(
                applicationContext.resources,
                R.mipmap.ic_launcher
            )
        )
        .setContentTitle(contentTitle)
        .setContentText(messageBody)
        .setContentIntent(contentPendingIntent)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)

    notify(NOTIFICATION_ID, builder.build())
}

fun NotificationManager.createChannel(
    channelId: String,
    channelName: String,
    channelDescription: String
) {
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            setShowBadge(false)
            enableLights(true)
            lightColor = Color.RED
            enableVibration(true)
            description = channelDescription
        }

        createNotificationChannel(notificationChannel)
    }
}