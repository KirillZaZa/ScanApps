package com.kizadev.scanapps.repository

import com.kizadev.domain.model.AppSettings
import com.kizadev.domain.model.PhoneApps
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
    private val appStorage: AppStorage,
    private val prefStorage: PrefStorage
) : AppRepository, SettingsRepository {

    override fun getApps(): Flow<PhoneApps> {
        return appStorage.getApps()
    }

    override fun getAppSettings(): StateFlow<AppSettings> {
        return prefStorage.appSettings
    }

    override fun editAppSettings(appSettings: AppSettings) {
        prefStorage.isDarkMode = appSettings.isDarkMode
    }
}
