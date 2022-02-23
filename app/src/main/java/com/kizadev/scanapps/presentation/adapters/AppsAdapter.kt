package com.kizadev.scanapps.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.kizadev.domain.model.AppInfo
import com.kizadev.scanapps.R
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

fun appAdapterDelegate(listener: OnAppListener) =
    adapterDelegateViewBinding<AppInfo, AppInfo, ItemAppInfoBinding>(
        { layoutInflater, root -> ItemAppInfoBinding.inflate(layoutInflater, root, false) }
    ) {
        with(binding) {
            root.transitionName = context.getString(R.string.transition_root, item.size)
            appIcon.transitionName =
                context.getString(R.string.transition_icon, item.packageName)
            appName.transitionName = context.getString(R.string.transition_text, item.name)
        }
        binding.root.setOnClickListener {
            listener.onAppClick(
                item,
                binding.appIcon,
                binding.appName,
                binding.root
            )
        }
        val imgDrawable =
            context.packageManager.getApplicationIcon(item.packageName)
        with(binding) {
            appIcon.setImageDrawable(imgDrawable)
            appName.text = item.name
        }
    }
