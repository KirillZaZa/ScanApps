package com.kizadev.domain.model

data class AppInfo(
    val name: String,
    val size: String,
    val targetSdkVersion: String,
    val installDate: String,
    val packageName: String
)
