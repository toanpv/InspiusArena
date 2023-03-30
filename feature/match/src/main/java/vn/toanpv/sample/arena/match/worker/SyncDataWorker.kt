package vn.toanpv.sample.arena.match.worker

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.inject
import vn.toanpv.sample.arena.domain.team.sync.SyncDataInteract
import vn.toanpv.sample.arena.match.R

class SyncDataWorker(
    val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val syncDataNotifId = 10001
    private val syncDataNotifChannelId = "sync_data_notif_channel_id"

    private val syncDataInteract: SyncDataInteract by inject(SyncDataInteract::class.java)

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        if (syncDataInteract.execute())
            Result.success()
        else Result.retry()
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return ForegroundInfo(syncDataNotifId, getNotification(context))
    }

    private fun getNotification(context: Context): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                syncDataNotifChannelId,
                context.getString(R.string.sync_notification_channel_name),
                NotificationManager.IMPORTANCE_DEFAULT,
            ).apply {
                description = context.getString(R.string.sync_notification_channel_description)
            }
            val notificationManager: NotificationManager? =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
            notificationManager?.createNotificationChannel(channel)
        }

        return NotificationCompat.Builder(
            context,
            syncDataNotifChannelId,
        ).setSmallIcon(R.drawable.ic_notif_sync)
            .setContentTitle(context.getString(R.string.sync_notification_title))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT).build()
    }
}