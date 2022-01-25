package com.kizadev.domain.usecase

import com.kizadev.domain.model.AppSettings
import com.kizadev.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetAppSettingsUseCase @Inject constructor(
    private val repository: SettingsRepository
) {

    fun execute(): StateFlow<AppSettings> {
        return repository.getAppSettings()
    }
}
