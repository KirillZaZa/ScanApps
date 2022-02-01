package com.kizadev.scanapps.presentation.adapters.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.kizadev.domain.model.AppInfo
import com.kizadev.scanapps.R
import com.kizadev.scanapps.app.scope
import com.kizadev.scanapps.databinding.ItemAppInfoBinding
import com.kizadev.scanapps.presentation.adapters.listeners.OnAppListener
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AppViewHolder(
    private val binding: ItemAppInfoBinding,
    private val io: CoroutineDispatcher = Dispatchers.IO
) :
    RecyclerView.ViewHolder(binding.root) {

    private val context = binding.root.context
    private val scope = context.scope

    fun bind(appInfo: AppInfo, listener: OnAppListener) {
        with(binding) {

            root.transitionName = context.getString(R.string.transition_root, appInfo.size)
            appIcon.transitionName =
                context.getString(R.string.transition_icon, appInfo.packageName)
            appName.transitionName = context.getString(R.string.transition_text, appInfo.name)
        }
        binding.root.setOnClickListener {
            listener.onAppClick(
                appInfo,
                binding.appIcon,
                binding.appName,
                binding.root
            )
        }
        scope.launch {
            val imgDrawable = withContext(io) {
                context.packageManager.getApplicationIcon(appInfo.packageName)
            }
            with(binding) {
                appIcon.setImageDrawable(imgDrawable)
                appName.text = appInfo.name
            }
        }
    }
}
