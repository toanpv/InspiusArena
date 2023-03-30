package vn.toanpv.sample.arena.match.worker

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.graphics.Color.RED
import android.media.AudioAttributes
import android.media.AudioAttributes.CONTENT_TYPE_SONIFICATION
import android.media.AudioAttributes.USAGE_NOTIFICATION_EVENT
import android.media.RingtoneManager.TYPE_NOTIFICATION
import android.media.RingtoneManager.getDefaultUri
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.O
import android.os.Build.VERSION_CODES.S
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.DEFAULT_ALL
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.ListenableWorker.Result.success
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import vn.toanpv.sample.arena.core.ui.vectorToBitmap
import vn.toanpv.sample.arena.extension.parser.fromISO8601ZToCalendar
import vn.toanpv.sample.arena.match.R
import vn.toanpv.sample.arena.match.ui.match.model.MatchUpcoming
import vn.toanpv.sample.arena.match.worker.MatchReminderWork.Companion.NOTIFICATION_AWAY
import vn.toanpv.sample.arena.match.worker.MatchReminderWork.Companion.NOTIFICATION_HOME
import vn.toanpv.sample.arena.match.worker.MatchReminderWork.Companion.NOTIFICATION_ID
import vn.toanpv.sample.arena.match.worker.MatchReminderWork.Companion.NOTIFICATION_TIME
import java.util.concurrent.TimeUnit

class MatchReminderWork(context: Context, val params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        val id = inputData.getString(NOTIFICATION_ID) ?: ""
        sendNotification(id)

        return success()
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun sendNotification(id: String) {
        val intent = Intent(
            applicationContext, Class.forName("vn.toanpv.sample.arena.MainActivity")
        ).apply {
            flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
            putExtra(NOTIFICATION_ID, id)
        }

        val notificationManager =
            applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val bitmap = applicationContext.vectorToBitmap(R.drawable.ic_notif_match_reminder_small)
        val titleNotification = applicationContext.getString(R.string.reminder_notification_match)
        val subtitleNotification =
            params.inputData.getString(NOTIFICATION_HOME) + " vs " + params.inputData.getString(
                NOTIFICATION_AWAY
            ) + "\n" + params.inputData.getString(NOTIFICATION_TIME)
        val pendingIntent = if (SDK_INT >= S) {
            getActivity(applicationContext, 0, intent, PendingIntent.FLAG_MUTABLE)
        } else {
            getActivity(applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        val notification = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL)
            .setLargeIcon(bitmap).setSmallIcon(R.drawable.ic_notif_match_reminder_small)
            .setContentTitle(titleNotification).setContentText(subtitleNotification)
            .setDefaults(DEFAULT_ALL).setContentIntent(pendingIntent)
            .setCategory(NotificationCompat.CATEGORY_REMINDER).setAutoCancel(true)

        if (SDK_INT >= O) {
            notification.setChannelId(NOTIFICATION_CHANNEL)

            val ringtoneManager = getDefaultUri(TYPE_NOTIFICATION)
            val audioAttributes = AudioAttributes.Builder().setUsage(USAGE_NOTIFICATION_EVENT)
                .setContentType(CONTENT_TYPE_SONIFICATION).build()

            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL, NOTIFICATION_NAME, IMPORTANCE_HIGH
            ).apply {
                enableLights(true)
                lightColor = RED
                //enableVibration(true)
                vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
                setSound(ringtoneManager, audioAttributes)
            }
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(System.currentTimeMillis().toInt(), notification.build())
    }

    companion object {
        const val NOTIFICATION_ID = "appName_notification_id"
        const val NOTIFICATION_WORK = "match_notifier_work"
        const val NOTIFICATION_NAME = "match_notifier"
        const val NOTIFICATION_CHANNEL = "match_notifier_channel"

        const val NOTIFICATION_HOME = "notification_home"
        const val NOTIFICATION_AWAY = "notification_away"
        const val NOTIFICATION_TIME = "notification_time"
    }
}

fun Context.scheduleNotification(match: MatchUpcoming) {
    match.dateRaw.fromISO8601ZToCalendar()?.let {
        var delay = it.timeInMillis - System.currentTimeMillis()
        if (delay < TimeUnit.MINUTES.toMillis(1))
            return
        if (TimeUnit.MILLISECONDS.toMinutes(delay) > 5)
            delay -= TimeUnit.MINUTES.toMillis(5)
        val data = Data.Builder().putString(NOTIFICATION_ID, match.id)
            .putString(NOTIFICATION_HOME, match.homeName)
            .putString(NOTIFICATION_AWAY, match.homeName)
            .putString(NOTIFICATION_TIME, match.date + " - " + match.time).build()
        WorkManager.getInstance(this).enqueueUniqueWork(
            match.id,
            ExistingWorkPolicy.REPLACE,
            OneTimeWorkRequestBuilder<MatchReminderWork>().setInitialDelay(
                delay, TimeUnit.MILLISECONDS
            ).setInputData(data).build(),
        )
    }
}

fun Context.cancelRemindedNotification(matchId: String) {
    WorkManager.getInstance(this).cancelUniqueWork(matchId)
}