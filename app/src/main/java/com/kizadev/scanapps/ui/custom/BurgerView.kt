package com.kizadev.scanapps.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.kizadev.scanapps.databinding.BurgerViewBinding

class BurgerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : LinearLayout(context, attrs, defStyleAttrs) {

    val binding: BurgerViewBinding = BurgerViewBinding.inflate(LayoutInflater.from(context), this)
}
