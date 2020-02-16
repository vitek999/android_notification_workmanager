package ru.vstu.visdom.testnotificationswithworkmanager.workers

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import ru.vstu.visdom.testnotificationswithworkmanager.utils.NOTIFICATION_ID
import ru.vstu.visdom.testnotificationswithworkmanager.utils.createChannel
import ru.vstu.visdom.testnotificationswithworkmanager.utils.sendNotification

class NotificationWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    override fun doWork(): Result {
        val appContext = applicationContext

        val notificationManager =
            appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.cancel(NOTIFICATION_ID)

        notificationManager.createChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            CHANNEL_DESCRIPTION
        )

        notificationManager.sendNotification(
            "test",
            "test content",
            appContext,
            CHANNEL_ID
        )

        Log.d("TEST_NOTIFICATION", "notification is pushed")

        return Result.success()
    }

    companion object {
        private const val CHANNEL_ID = "test_channel_id"
        private const val CHANNEL_NAME = "test_channel_name"
        private const val CHANNEL_DESCRIPTION = "test_channel_description"
    }
}