package vn.toanpv.sample.arena.repository.data.preferences

import kotlinx.coroutines.flow.Flow

interface DataStoreSource {
    suspend fun getSyncStatus(): Flow<Boolean>

    suspend fun setSyncStatus(syncing: Boolean)

    suspend fun getReminderMatchIds(): Flow<Set<String>>

    suspend fun updateReminderMatchIds(reminderIds: Set<String>): Boolean
}