package com.kizadev.scanapps.presentation.viewmodel.base

import android.util.Log
import androidx.annotation.UiThread
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

    protected val _screenState = MutableStateFlow(initState)

    val screenState = _screenState.asStateFlow()

    @UiThread
    protected inline fun updateState(transform: (T) -> T) {
        _screenState.value = transform.invoke(screenState.value)
    }
}

private const val BASE_VIEW_MODEL_TAG = "BASE_VIEW_MODEL_TAG"
