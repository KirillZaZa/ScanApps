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
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel(
    getAppSettingsUseCase: GetAppSettingsUseCase,
    private val getAppsUseCase: GetAppsUseCase,
    private val updateAppSettingsUseCase: UpdateAppSettingsUseCase
) : BaseViewModel<ScanScreen>(
    initState = ScanScreen()
) {

    val themeState =
        getAppSettingsUseCase.execute()

    private val eventChannel = Channel<ScanEvent>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    fun handleScanning() {
        val job = viewModelScope.launch(baseContext) {
            updateState {
                it.copy(isScanning = true)
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
                            eventChannel.send(ScanEvent.ShowApps)
                        }

                        is Failure -> {
                            updateState { it.copy(isScanning = false) }
                            eventChannel.send(ScanEvent.ShowFailure(scanResult.reason.message!!))
                        }
                        is Error -> {
                            updateState { it.copy(isScanning = false) }
                            Log.e("MainViewModel", "handleScanning: ${scanResult.msg}")
                        }
                    }
                }
        }
        activeJobsList.addFirst(job)
    }

    fun handleCancelScanning() {
        viewModelScope.launch(baseContext) {
            updateState { it.copy(isScanning = false) }
            activeJobsList.pop().cancelAndJoin()
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

    fun handleListCanBeShown() {
        updateState { it.copy(isListCanBeShown = true) }
    }
}

sealed class ScanEvent {

    object ShowApps : ScanEvent()

    data class ShowFailure(val msg: String) : ScanEvent()
}
