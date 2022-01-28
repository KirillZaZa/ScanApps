package com.kizadev.scanapps.ui.custom

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.kizadev.scanapps.R
import com.kizadev.scanapps.databinding.SwitchThemeViewBinding
import kotlin.properties.Delegates

class SwitchThemeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttrs) {

    val switchBinding: SwitchThemeViewBinding =
        SwitchThemeViewBinding.inflate(LayoutInflater.from(context), this)

    private val nightThemeName = context.getString(R.string.dark_theme_name)
    private val dayThemeName = context.getString(R.string.light_theme_name)
    private val moonDrawable = ContextCompat.getDrawable(context, R.drawable.ic_moon)
    private val sunDrawable = ContextCompat.getDrawable(context, R.drawable.ic_sun)

    private val iconMode = switchBinding.iconMode
    private val textMode = switchBinding.textMode

    private val startXIcon = 0f
    private var endXIcon by Delegates.notNull<Float>()

    private var startXText = 0f
    private var endXText by Delegates.notNull<Float>()

    private lateinit var iconAnimator: ObjectAnimator
    private lateinit var textAnimator: ObjectAnimator

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        endXIcon = ((width - iconMode.measuredWidth) - iconMode.marginStart * 2).toFloat()
        endXText = (-textMode.measuredWidth / 2 + textMode.marginEnd * 2).toFloat()

        initAnimators()
    }

    private fun initAnimators() {
        iconAnimator = ObjectAnimator.ofFloat(
            iconMode,
            View.TRANSLATION_X,
            startXIcon,
            endXIcon
        ).apply {
            duration = 500
            interpolator = FastOutSlowInInterpolator()
        }

        textAnimator = ObjectAnimator.ofFloat(
            textMode,
            View.TRANSLATION_X,
            startXText,
            endXText
        ).apply {
            duration = 500
            interpolator = FastOutSlowInInterpolator()
        }
    }

    fun switchToNightMode() {
        textMode.text = nightThemeName
        iconMode.setImageDrawable(moonDrawable)
        iconAnimator.start()
        textAnimator.start()
    }

    fun switchToDayMode() {
        textMode.text = dayThemeName
        iconMode.setImageDrawable(sunDrawable)
        iconAnimator.reverse()
        textAnimator.reverse()
    }
}
