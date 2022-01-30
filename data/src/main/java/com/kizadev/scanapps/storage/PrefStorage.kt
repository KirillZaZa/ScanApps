package com.kizadev.scanapps.storage

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kizadev.domain.model.AppSettings
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.*

class PrefStorage(
    context: Context
) {
    private val PREFS_NAME = "SETTINGS"
    private val PREF_TAG = PrefStorage::javaClass.name
    private val Context.dataStore by preferencesDataStore(name = PREFS_NAME)
    val dataStore = context.dataStore

    private val errorHandler = CoroutineExceptionHandler { _, th ->
        Log.e(PREF_TAG, "$th")
    }

    internal val scope = CoroutineScope(SupervisorJob() + errorHandler + Dispatchers.IO)

    var isDarkMode by PrefDelegate(false)

    val appSettings: StateFlow<AppSettings>
        get() {
            val isDark =
                dataStore.data.map {
                    it[booleanPreferencesKey(this::isDarkMode.name)] ?: false
                }

            return isDark.map { dark -> AppSettings(dark) }
                .distinctUntilChanged()
                .stateIn(scope, SharingStarted.Eagerly, AppSettings(isDarkMode))
        }
}
