package com.kizadev.scanapps.storage

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import com.kizadev.domain.model.AppInfo
import com.kizadev.domain.model.Apps
import com.kizadev.scanapps.ext.toInstallTime
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.text.DecimalFormat
import javax.inject.Inject
import kotlin.math.log10
import kotlin.math.pow

class AppStorage @Inject constructor(
    private val context: Context
) {

    private val packageManager = context.packageManager

    @SuppressLint("QueryPermissionsNeeded")
    private val packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

    fun getApps(): Flow<Apps> = flow {
        delay(5000)
        val apps = Apps(
            packages.map { info ->
                AppInfo(
                    name = info.name,
                    size = getAppSize(
                        info.packageName
                    ),
                    targetSdkVersion = info.targetSdkVersion.toString(),
                    packageName = info.packageName,
                    installDate = getInstallDate(
                        info.packageName
                    )
                )
            }
        )
        emit(apps)
    }

    private fun getInstallDate(packageName: String): String {
        return packageManager
            .getPackageInfo(packageName, 0)
            .firstInstallTime
            .toInstallTime()
    }

    private fun getAppSize(packageName: String): String {
        return try {
            val size = File(
                context.packageManager.getApplicationInfo(packageName, 0).publicSourceDir
            ).length()
            calculateApkSize(size)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            calculateApkSize(INVALID_SIZE)
        }
    }

    private fun calculateApkSize(size: Long): String {
        if (size <= INVALID_SIZE) return ZERO_BYTES

        val digitGroups = (log10(size.toDouble()) / log10(K_BYTE)).toInt()

        return DecimalFormat(FILE_DECIMAL_PATTERN)
            .format(size / 1024.0.pow(digitGroups.toDouble()))
            .toString() + " " + units[digitGroups]
    }

    companion object {
        private const val INVALID_SIZE = -1L
        private const val K_BYTE = 1024.0
        private const val FILE_DECIMAL_PATTERN = "#,##.##"
        private const val ZERO_BYTES = "0 bytes"
        private val units = arrayOf(
            "B",
            "KB",
            "MB",
            "GB",
        )
    }
}
