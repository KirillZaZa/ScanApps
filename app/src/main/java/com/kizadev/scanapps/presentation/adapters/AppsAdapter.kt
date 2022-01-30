package com.kizadev.scanapps.presentation.adapters

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kizadev.domain.model.AppInfo
import com.kizadev.scanapps.databinding.ItemAppInfoBinding
import com.kizadev.scanapps.ext.dpToIntPx
import com.kizadev.scanapps.presentation.viewholders.AppViewHolder

class AppsAdapter : ListAdapter<AppInfo, AppViewHolder>(AppsCallback()) {

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
        viewHolder.bind(currentList[position])
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

class ItemOffsetDecoration(
    private val mItemOffset: Int = 16f.dpToIntPx(),
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val left = mItemOffset
        val top = mItemOffset
        val right = mItemOffset
        val bottom = mItemOffset
        outRect.set(
            left,
            top,
            right,
            bottom
        )
    }
}
