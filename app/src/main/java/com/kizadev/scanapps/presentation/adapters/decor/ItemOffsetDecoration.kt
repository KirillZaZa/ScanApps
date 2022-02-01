package com.kizadev.scanapps.presentation.adapters.decor

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kizadev.scanapps.ext.dpToIntPx

class ItemOffsetDecoration(
    private val mItemOffset: Int = 16f.dpToIntPx(),
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        when (parent.getChildAdapterPosition(view)) {
            0, 1 -> {
                outRect.set(
                    mItemOffset,
                    mItemOffset * 10,
                    mItemOffset,
                    mItemOffset
                )
            }
            else -> {
                outRect.set(
                    mItemOffset,
                    mItemOffset,
                    mItemOffset,
                    mItemOffset
                )
            }
        }
    }
}
