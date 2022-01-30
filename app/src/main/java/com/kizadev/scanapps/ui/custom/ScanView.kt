package com.kizadev.scanapps.ui.custom

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnCancel
import androidx.core.view.ViewCompat
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.kizadev.scanapps.R
import com.kizadev.scanapps.databinding.ScanViewBinding
import kotlin.properties.Delegates

class ScanView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttrs) {

    val viewBinding: ScanViewBinding =
        ScanViewBinding.inflate(LayoutInflater.from(context), this)

    private val gradientCircle = viewBinding.circle

    private val startRotationValue = 0f
    private val endRotationValue = 360f

    private var endTranslationY by Delegates.notNull<Float>()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        endTranslationY = rootView.height.toFloat()
    }

    private val rotationAnimator = ObjectAnimator.ofFloat(
        gradientCircle,
        View.ROTATION,
        startRotationValue,
        endRotationValue
    ).apply {
        duration = 500
        repeatCount = Animation.INFINITE
        interpolator = LinearInterpolator()
        doOnCancel {
            gradientCircle.animate().rotation(endRotationValue)
                .setDuration(300)
                .setInterpolator(LinearInterpolator())
                .start()
        }
    }

    fun startAnimateScanning() {
        rotationAnimator.start()
        with(viewBinding) {
            textScan.text = context.getString(R.string.text_cancel)
        }
    }

    fun cancelAnimateScanning() {
        with(viewBinding) {
            textScan.text = context.getString(R.string.text_scan)
        }
        rotationAnimator.cancel()
    }

    fun finishAnimateScanning() {
        rotationAnimator.cancel()
        hideView(this)
    }

    private fun hideView(view: View) {
        ViewCompat.animate(view)
            .translationY(endTranslationY)
            .setDuration(500)
            .setInterpolator(FastOutSlowInInterpolator())
            .withEndAction {
                view.visibility = View.GONE
            }
            .start()
    }
}
