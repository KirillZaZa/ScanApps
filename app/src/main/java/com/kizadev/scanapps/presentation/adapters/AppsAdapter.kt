package com.kizadev.scanapps.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.kizadev.domain.model.AppInfo
import com.kizadev.scanapps.databinding.ItemAppInfoBinding
import com.kizadev.scanapps.presentation.adapters.listeners.OnAppListener
import com.kizadev.scanapps.presentation.adapters.viewholders.AppViewHolder

class AppsAdapter(private val onAppListener: OnAppListener) :
    ListAdapter<AppInfo, AppViewHolder>(AppsCallback()) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AppViewHolder {
        val binding =
            ItemAppInfoBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )

        return AppViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: AppViewHolder, position: Int) {
        viewHolder.bind(currentList[position], onAppListener)
    }
}

class AppsCallback : DiffUtil.ItemCallback<AppInfo>() {

    override fun areItemsTheSame(oldItem: AppInfo, newItem: AppInfo): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: AppInfo, newItem: AppInfo): Boolean {
        return oldItem == newItem
    }
}
