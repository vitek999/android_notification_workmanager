package ru.vstu.visdom.testnotificationswithworkmanager

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import org.joda.time.DateTime
import org.joda.time.Duration
import ru.vstu.visdom.testnotificationswithworkmanager.workers.NotificationWorker
import java.util.concurrent.TimeUnit


class MainViewModel(application: Application) : AndroidViewModel(application) {

    fun setUpNotification() {
        val delay = if (DateTime.now().hourOfDay < SELF_REMINDER_HOUR) {
            Duration(
                DateTime.now(),
                DateTime.now().withTimeAtStartOfDay().plusHours(SELF_REMINDER_HOUR)
            ).standardMinutes
        } else {
            Duration(
                DateTime.now(),
                DateTime.now().withTimeAtStartOfDay().plusDays(1).plusHours(
                    SELF_REMINDER_HOUR
                )
            ).standardMinutes
        }

        val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(24, TimeUnit.HOURS, PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS,
            TimeUnit.MILLISECONDS)
            .setInitialDelay(delay, TimeUnit.MINUTES)
            .addTag("send_reminder_periodic")
            .build()

        WorkManager.getInstance(getApplication())
            .enqueueUniquePeriodicWork("send_reminder_periodic", ExistingPeriodicWorkPolicy.REPLACE, workRequest);
    }

    companion object {
        private const val SELF_REMINDER_HOUR = 10
    }
}