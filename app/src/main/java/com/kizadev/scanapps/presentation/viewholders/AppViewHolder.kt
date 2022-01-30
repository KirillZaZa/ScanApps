package com.kizadev.scanapps.presentation.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.kizadev.domain.model.AppInfo
import com.kizadev.scanapps.app.scope
import com.kizadev.scanapps.databinding.ItemAppInfoBinding
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

    fun bind(appInfo: AppInfo) {
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
