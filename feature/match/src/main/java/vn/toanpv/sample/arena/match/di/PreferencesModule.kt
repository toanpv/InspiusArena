package vn.toanpv.sample.arena.match.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module
import vn.toanpv.sample.arena.match.repository.data.preference.DataStoreAppImpl
import vn.toanpv.sample.arena.repository.data.preferences.DataStoreSource

val preferencesModule = module {

    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create(
            null,
            listOf(),
            CoroutineScope(Dispatchers.IO + SupervisorJob())
        ) {
            get<Context>().preferencesDataStoreFile("InspiusDataStore")
        }
    }

    single<DataStoreSource> { DataStoreAppImpl(get()) }
}