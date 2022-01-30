package com.kizadev.scanapps.ui.custom

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnStart
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

    private var startXText = 1000f
    private var endXTextNight by Delegates.notNull<Float>()
    private var endXTextDay = 0f

    init {
        switchBinding.iconMode.visibility = View.INVISIBLE
        switchBinding.textMode.visibility = View.INVISIBLE
    }

    private lateinit var iconAnimator: ObjectAnimator
    private lateinit var nightTextAnimator: ObjectAnimator
    private lateinit var dayTextAnimator: ObjectAnimator

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        endXIcon = ((width - iconMode.measuredWidth) - iconMode.marginStart * 2).toFloat()
        endXTextNight = (-textMode.measuredWidth / 2 + textMode.marginEnd * 2).toFloat()
        initAnimators()
    }

    private fun initAnimators() {
        iconAnimator = ObjectAnimator.ofFloat(
            iconMode,
            View.TRANSLATION_X,
            startXIcon,
            endXIcon
        ).apply {
            duration = 100
            interpolator = FastOutSlowInInterpolator()

            doOnStart {
                textMode.visibility = View.VISIBLE
                iconMode.visibility = View.VISIBLE
            }
        }

        nightTextAnimator = ObjectAnimator.ofFloat(
            textMode,
            View.TRANSLATION_X,
            -startXText,
            endXTextNight
        ).apply {
            duration = 100
            interpolator = FastOutSlowInInterpolator()

            doOnStart {
                textMode.visibility = View.VISIBLE
                iconMode.visibility = View.VISIBLE
            }
        }

        dayTextAnimator = ObjectAnimator.ofFloat(
            textMode,
            View.TRANSLATION_X,
            startXText,
            endXTextDay
        ).apply {
            duration = 100
            interpolator = FastOutSlowInInterpolator()

            doOnStart {
                textMode.visibility = View.VISIBLE
                iconMode.visibility = View.VISIBLE
            }
        }
    }

    fun switch(mode: Boolean) {
        if (mode) {
            textMode.text = nightThemeName
            iconMode.setImageDrawable(moonDrawable)
            iconAnimator.start()
            nightTextAnimator.start()
        } else {
            textMode.text = dayThemeName
            iconMode.setImageDrawable(sunDrawable)
            iconAnimator.reverse()
            dayTextAnimator.start()
        }
    }
}
