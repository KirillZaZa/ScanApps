package com.kizadev.scanapps.presentation.viewmodel.state

import com.kizadev.domain.model.AppInfo

data class ScanScreen(
    val isScanning: Boolean = false,
    val isListCanBeShown: Boolean = false,
    val appList: List<AppInfo> = emptyList()
)
