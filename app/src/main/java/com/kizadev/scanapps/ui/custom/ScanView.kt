package com.kizadev.scanapps.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.kizadev.scanapps.R
import com.kizadev.scanapps.databinding.ScanViewBinding

class ScanView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttrs) {

    val viewBinding: ScanViewBinding

    init {
        viewBinding = ScanViewBinding.inflate(LayoutInflater.from(context), this)
        setup()
    }

    private fun setup() {
        elevation = 50f
    }

    fun startAnimateScanning() {
       //todo
    }

    fun stopAnimateScanning() {
        //todo
    }
}
