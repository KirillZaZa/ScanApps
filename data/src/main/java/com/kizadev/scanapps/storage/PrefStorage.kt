package com.kizadev.scanapps.storage

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.kizadev.domain.model.AppSettings
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrefStorage @Inject constructor(
    internal val dataStore: DataStore<Preferences>
) {

    private val errorHandler = CoroutineExceptionHandler { _, th ->
        Log.e(PREF_TAG, "$th")
    }

    internal val scope = CoroutineScope(SupervisorJob() + errorHandler + Dispatchers.IO)

    var isDarkMode by PrefDelegate(false)

    val appSettings: StateFlow<AppSettings>
        get() {
            val isDark =
                dataStore.data.map { it[booleanPreferencesKey(this::isDarkMode.name)] ?: false }

            return isDark.map { dark -> AppSettings(dark) }
                .distinctUntilChanged()
                .stateIn(scope, SharingStarted.Eagerly, AppSettings(isDarkMode))
        }
}

private val PREF_TAG = PrefStorage::javaClass.name
