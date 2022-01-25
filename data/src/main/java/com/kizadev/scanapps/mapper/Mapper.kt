package com.kizadev.scanapps.mapper

import android.content.pm.ApplicationInfo
import com.kizadev.domain.model.AppInfo

fun ApplicationInfo.toAppInfo(
    appSize: String,
    date: String
): AppInfo {

    return AppInfo(
        name = this.name,
        size = appSize,
        targetSdkVersion = this.targetSdkVersion.toString(),
        packageName = this.packageName,
        installDate = date
    )
}
