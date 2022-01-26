package com.kizadev.scanapps.presentation.viewmodel.state

import com.kizadev.domain.model.AppInfo

data class ScanScreen(
    val isScanning: Boolean = false,
    val isDarkMode: Boolean = false,
    val isScanFailed: Boolean = false,
    val scanFailedMsg: String? = null,
    val appList: List<AppInfo> = emptyList()
)
