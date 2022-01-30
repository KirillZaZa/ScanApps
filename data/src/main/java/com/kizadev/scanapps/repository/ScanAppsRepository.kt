package com.kizadev.scanapps.repository

import android.content.Context
import com.kizadev.domain.model.AppSettings
import com.kizadev.domain.model.Apps
import com.kizadev.domain.repository.AppRepository
import com.kizadev.domain.repository.SettingsRepository
import com.kizadev.scanapps.storage.AppStorage
import com.kizadev.scanapps.storage.PrefStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScanAppsRepository @Inject constructor(
    context: Context,
    private val appStorage: AppStorage = AppStorage(context),
    private val prefStorage: PrefStorage
) : AppRepository, SettingsRepository {

    override fun getApps(): Flow<Apps> {
        return appStorage.getApps()
    }

    override fun getAppSettings(): StateFlow<AppSettings> {
        return prefStorage.appSettings
    }

    override fun editAppSettings(appSettings: AppSettings) {
        prefStorage.isDarkMode = appSettings.isDarkMode
    }
}
