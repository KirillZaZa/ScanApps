package com.kizadev.scanapps.ext

import android.content.res.Resources
import android.util.TypedValue

fun Float.dpToPx(): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    ).toInt()
}
