package com.kizadev.scanapps.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.kizadev.scanapps.databinding.SwitchThemeViewBinding

class SwitchThemeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttrs) {

    val switchBinding: SwitchThemeViewBinding

    init {
        switchBinding = SwitchThemeViewBinding.inflate(LayoutInflater.from(context), this)
    }
}
