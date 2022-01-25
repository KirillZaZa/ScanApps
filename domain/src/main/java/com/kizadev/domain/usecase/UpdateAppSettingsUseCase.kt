package com.kizadev.domain.usecase

import com.kizadev.domain.model.AppSettings
import com.kizadev.domain.repository.SettingsRepository

class UpdateAppSettingsUseCase(
    private val repository: SettingsRepository
) {

    fun execute(appSettings: AppSettings) {
        repository.editAppSettings(appSettings)
    }
}
