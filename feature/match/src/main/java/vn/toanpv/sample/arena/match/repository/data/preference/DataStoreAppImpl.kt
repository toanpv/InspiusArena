package vn.toanpv.sample.arena.match.repository.data.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import vn.toanpv.sample.arena.repository.data.preferences.DataStoreSource

class DataStoreAppImpl(private val dataStore: DataStore<Preferences>) : DataStoreSource {
    private companion object {
        val KEY_SYNC_STATUS = booleanPreferencesKey("key_sync_status")
        val KEY_REMINDER_MATCH_IDS = stringSetPreferencesKey("key_reminder_match_ids")
    }

    override suspend fun getSyncStatus(): Flow<Boolean> {
        return dataStore.data.map { pref ->
            pref[KEY_SYNC_STATUS] ?: false
        }
    }

    override suspend fun setSyncStatus(syncing: Boolean) {
        dataStore.edit { pref -> pref[KEY_SYNC_STATUS] = syncing }
    }

    override suspend fun getReminderMatchIds(): Flow<Set<String>> {
        return dataStore.data.map { pref ->
            pref[KEY_REMINDER_MATCH_IDS] ?: setOf()
        }
    }

    override suspend fun updateReminderMatchIds(reminderIds: Set<String>): Boolean {
        dataStore.edit { pref ->
            pref[KEY_REMINDER_MATCH_IDS] = reminderIds
        }

        return true

    }
}