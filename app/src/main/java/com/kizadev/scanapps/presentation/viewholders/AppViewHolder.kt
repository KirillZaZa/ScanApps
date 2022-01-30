package com.kizadev.scanapps.presentation.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.kizadev.domain.model.AppInfo
import com.kizadev.scanapps.databinding.ItemAppInfoBinding

class AppViewHolder(private val binding: ItemAppInfoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val context = binding.root.context

    fun bind(appInfo: AppInfo) {
        val imgDrawable = context.packageManager.getApplicationIcon(appInfo.packageName)
        with(binding) {
            appIcon.setImageDrawable(imgDrawable)
            appName.text = appInfo.name
        }
    }
}
