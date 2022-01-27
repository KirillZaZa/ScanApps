package com.kizadev.scanapps.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.updatePadding
import com.kizadev.scanapps.R
import com.kizadev.scanapps.ext.dpToPx

class SwitchThemeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttrs) {

    private val elevationValue = 50f
    private val paddingVertical = 8

    private val firstChild: ImageView?
        get() = if (childCount > 0) getChildAt(0) as ImageView? else null

    private val secondChild: TextView?
        get() = if (childCount > 1) getChildAt(1) as TextView? else null

    init {
        View.inflate(context, R.layout.layout_switch_theme, this)
        context.withStyledAttributes(attrs, R.styleable.SwitchTheme, defStyleAttrs) {
            setThemeNameToChild(getResourceId(R.styleable.SwitchTheme_theme_name, 0))
            setImageToChild(getResourceId(R.styleable.SwitchTheme_icon_theme, 0))
            background = ContextCompat.getDrawable(
                context,
                getResourceId(R.styleable.SwitchTheme_switch_background, 0)
            )
        }
        setPaddingVertical()
        elevation = elevationValue
    }

    private fun setThemeNameToChild(resourceId: Int) {
        secondChild?.text = context.getString(resourceId)
    }

    private fun setImageToChild(resourceId: Int) {
        firstChild?.background = ContextCompat.getDrawable(context, resourceId)
    }

    private fun setPaddingVertical() {
        updatePadding(
            top = context.dpToPx(paddingVertical),
            bottom = context.dpToPx(paddingVertical)
        )
    }

    private fun measureChild() {
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // TODO
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }
}
