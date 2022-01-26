package com.kizadev.scanapps.utils

import android.os.Parcelable
import com.kizadev.domain.model.AppInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class AppDetails(
    val appName: String,
    val targetSdk: String,
    val size: String,
    val installationDate: String
) : Parcelable

fun AppInfo.asAppDetails(): AppDetails = AppDetails(
    appName = this.name,
    targetSdk = this.targetSdkVersion,
    size = this.size,
    installationDate = this.installDate
)
