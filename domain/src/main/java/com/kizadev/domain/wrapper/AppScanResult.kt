package com.kizadev.domain.wrapper

sealed class AppScanResult<out T, out E>

data class Success<out T>(val value: T) : AppScanResult<T, Nothing>()
data class Failure<out E>(val reason: E) : AppScanResult<Nothing, E>()
data class Error<out E>(val msg: String) : AppScanResult<Nothing, E>()
