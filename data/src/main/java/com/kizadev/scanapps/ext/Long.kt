package com.kizadev.scanapps.ext

import java.text.SimpleDateFormat
import java.util.*

fun Long?.toInstallTime(): String {
    this ?: return "Install time undefined"
    val date = Date(this * 1000)
    val pattern = "MM-dd-yyyy"
    val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())

    return dateFormat.format(date)
}
