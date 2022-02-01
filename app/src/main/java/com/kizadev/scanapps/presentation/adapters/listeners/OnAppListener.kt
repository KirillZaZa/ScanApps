package com.kizadev.scanapps.presentation.adapters.listeners

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.kizadev.domain.model.AppInfo

class OnAppListener(val clickListener: (AppInfo, ImageView, TextView, View) -> Unit) {
    fun onAppClick(appInfo: AppInfo, imageView: ImageView, textView: TextView, root: View) =
        clickListener(appInfo, imageView, textView, root)
}
