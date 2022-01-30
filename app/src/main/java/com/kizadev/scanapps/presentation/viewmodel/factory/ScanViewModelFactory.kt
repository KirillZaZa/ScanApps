package com.kizadev.scanapps.presentation.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kizadev.domain.usecase.GetAppSettingsUseCase
import com.kizadev.domain.usecase.GetAppsUseCase
import com.kizadev.domain.usecase.UpdateAppSettingsUseCase
import com.kizadev.scanapps.presentation.viewmodel.MainViewModel
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.lang.IllegalArgumentException

class ScanViewModelFactory @AssistedInject constructor(
    private val getAppSettingsUseCase: GetAppSettingsUseCase,
    private val getAppsUseCase: GetAppsUseCase,
    private val updateAppSettingsUseCase: UpdateAppSettingsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java))
            return MainViewModel(
                getAppSettingsUseCase, getAppsUseCase, updateAppSettingsUseCase
            ) as T
        throw IllegalArgumentException()
    }

    @AssistedFactory
    interface Factory {
        fun create(): ScanViewModelFactory
    }
}
