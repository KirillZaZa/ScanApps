package com.kizadev.domain.repository

import com.kizadev.domain.model.AppSettings
import kotlinx.coroutines.flow.StateFlow

interface SettingsRepository {
    fun getAppSettings(): StateFlow<AppSettings>

    fun editAppSettings(appSettings: AppSettings)
}
