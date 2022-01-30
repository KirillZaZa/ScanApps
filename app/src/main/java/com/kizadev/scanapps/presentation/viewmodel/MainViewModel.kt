package com.kizadev.scanapps.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.kizadev.domain.model.AppSettings
import com.kizadev.domain.usecase.GetAppSettingsUseCase
import com.kizadev.domain.usecase.GetAppsUseCase
import com.kizadev.domain.usecase.UpdateAppSettingsUseCase
import com.kizadev.domain.wrapper.Error
import com.kizadev.domain.wrapper.Failure
import com.kizadev.domain.wrapper.Success
import com.kizadev.scanapps.presentation.viewmodel.base.BaseViewModel
import com.kizadev.scanapps.presentation.viewmodel.state.ScanScreen
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch

class MainViewModel(
    private val getAppSettingsUseCase: GetAppSettingsUseCase,
    private val getAppsUseCase: GetAppsUseCase,
    private val updateAppSettingsUseCase: UpdateAppSettingsUseCase
) : BaseViewModel<ScanScreen>(
    initState = ScanScreen(),
) {

    val themeState =
        getAppSettingsUseCase.execute()

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
                        is Error -> {
                            Log.e("ScanResult", "handleScanning: ${scanResult.msg}")
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
                isDarkMode = !themeState.value.isDarkMode
            )
            updateAppSettingsUseCase.execute(updatedSettings)
        }
    }
}
