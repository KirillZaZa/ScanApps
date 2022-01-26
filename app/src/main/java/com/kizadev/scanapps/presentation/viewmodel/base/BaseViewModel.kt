package com.kizadev.scanapps.presentation.viewmodel.base

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*

abstract class BaseViewModel<T>(
    initState: T,
) : ViewModel() {

    private val errorHandler = CoroutineExceptionHandler { _, th ->
        th.localizedMessage?.let { Log.e(BASE_VIEW_MODEL_TAG, it) }
    }

    protected val activeJobsList = LinkedList<Job>()

    protected val baseContext = Dispatchers.Main + errorHandler

    protected val _scanScreenState = MutableStateFlow(initState)
    val scanScreenState = _scanScreenState.asStateFlow()

    protected inline fun updateState(transform: (T) -> T) {
        val updatedState = transform.invoke(scanScreenState.value)
        _scanScreenState.value = updatedState
    }
}

private const val BASE_VIEW_MODEL_TAG = "BASE_VIEW_MODEL_TAG"
