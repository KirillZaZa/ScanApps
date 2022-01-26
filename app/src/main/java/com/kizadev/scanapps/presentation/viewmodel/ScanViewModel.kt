package com.kizadev.scanapps.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.kizadev.domain.model.AppSettings
import com.kizadev.domain.usecase.GetAppSettingsUseCase
import com.kizadev.domain.usecase.GetAppsUseCase
import com.kizadev.domain.usecase.UpdateAppSettingsUseCase
import com.kizadev.domain.wrapper.Failure
import com.kizadev.domain.wrapper.Success
import com.kizadev.scanapps.presentation.viewmodel.base.BaseViewModel
import com.kizadev.scanapps.presentation.viewmodel.state.ScanScreen
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScanViewModel @Inject constructor(
    private val getAppSettingsUseCase: GetAppSettingsUseCase,
    private val getAppsUseCase: GetAppsUseCase,
    private val updateAppSettingsUseCase: UpdateAppSettingsUseCase
) : BaseViewModel<ScanScreen>(
    initState = ScanScreen(),
) {

    init {
        getAppSettings()
    }

    private fun getAppSettings() =
        viewModelScope.launch(baseContext) {
            getAppSettingsUseCase.execute()
                .collect { settings ->
                    updateState {
                        it.copy(
                            isDarkMode = settings.isDarkMode
                        )
                    }
                }
        }

    fun handleScanning() {
        val job = viewModelScope.launch(baseContext) {
            updateState {
                it.copy(
                    isScanning = true,
                    isScanFailed = false,
                    scanFailedMsg = null
                )
            }

            getAppsUseCase.execute()
                .collect { scanResult ->
                    when (scanResult) {

                        is Success -> {
                            updateState {
                                it.copy(
                                    isScanning = false,
                                    appList = scanResult.value.appsList
                                )
                            }
                        }

                        is Failure -> {
                            updateState {
                                it.copy(
                                    isScanning = false,
                                    isScanFailed = true,
                                    scanFailedMsg = scanResult.reason.message
                                )
                            }
                        }
                        else -> {
                            return@collect
                        }
                    }
                }
        }
        activeJobsList.addFirst(job)
    }

    fun handleCancelScanning() {
        viewModelScope.launch(baseContext) {
            activeJobsList.pop().cancelAndJoin()
            updateState {
                it.copy(
                    isScanning = false,
                    appList = emptyList()
                )
            }
        }
    }

    fun handleAppTheme() {
        viewModelScope.launch(baseContext) {
            val updatedSettings = AppSettings(
                isDarkMode = !_scanScreenState.value.isDarkMode
            )
            updateAppSettingsUseCase.execute(updatedSettings)
            updateState {
                it.copy(
                    isDarkMode = !it.isDarkMode
                )
            }
        }
    }
}
