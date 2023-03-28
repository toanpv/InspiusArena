package vn.inspius.toanpv.arena.match.worker

import android.content.Context
import androidx.startup.Initializer
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import androidx.work.WorkManagerInitializer

class SyncDataWorkerInitializer : Initializer<WorkManager> {
    private val SyncWorkerName = "SyncWorkerInitializer"

    override fun create(context: Context): WorkManager {
        return WorkManager.getInstance(context).apply {
            enqueueUniqueWork(
                SyncWorkerName,
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequestBuilder<SyncDataWorker>()
                    .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                    .setConstraints(
                        Constraints.Builder()
                            .setRequiredNetworkType(NetworkType.CONNECTED)
                            .build()
                    )
                    .build(),
            )
        }
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(WorkManagerInitializer::class.java)
    }

}