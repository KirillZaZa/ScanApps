package com.kizadev.scanapps.ext

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import com.kizadev.scanapps.app.ScanApplication
import kotlinx.coroutines.CoroutineScope

fun Float.dpToPx(): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )
}

fun Float.dpToIntPx(): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    ).toInt()
}

fun Int.pxToDp(): Int {
    return (this / Resources.getSystem().displayMetrics.density).toInt()
}

