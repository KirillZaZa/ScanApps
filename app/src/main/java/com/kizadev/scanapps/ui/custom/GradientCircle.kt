package com.kizadev.scanapps.ui.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.kizadev.scanapps.R
import kotlin.properties.Delegates

class GradientCircle @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : View(context, attrs, defStyleAttrs) {

    private var circleCenterX by Delegates.notNull<Float>()
    private var circleCenterY by Delegates.notNull<Float>()
    private var circleRadius by Delegates.notNull<Float>()

    private var circleStrokeWidth by Delegates.notNull<Float>()
    private val shadowRadius = 50f

    private val yellow = ContextCompat.getColor(context, R.color.light_yellow)
    private val orange = ContextCompat.getColor(context, R.color.orange)
    private val pink = ContextCompat.getColor(context, R.color.dark_pink)
    private val purple = ContextCompat.getColor(context, R.color.purple)
    private val black = ContextCompat.getColor(context, R.color.black)

    private val startX = 0f
    private val startY = 0f
    private var endX by Delegates.notNull<Float>()
    private var endY by Delegates.notNull<Float>()

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.GradientCircle)
        circleStrokeWidth =
            typedArray.getDimension(R.styleable.GradientCircle_circle_border_radius, 10f)
        typedArray.recycle()
    }

    private val paintFirstCircle = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    private val paintBorderCircle = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = circleStrokeWidth
    }

    private val paintShadow = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 10f
        setShadowLayer(20f, -5f, -5f, black)
    }

    private fun createCircleShader() = LinearGradient(
        startX - paddingLeft,
        startY - paddingTop,
        endX + paddingRight,
        endY + paddingBottom,
        intArrayOf(
            yellow, orange, pink, purple
        ),
        floatArrayOf(0.0f, 0.34f, 0.66f, 1.0f), Shader.TileMode.CLAMP
    )

    private fun createBorderShader() = LinearGradient(
        startX,
        startY,
        endX,
        endY,
        intArrayOf(
            yellow, orange, pink, purple
        ),
        floatArrayOf(0.19f, 0.42f, 0.61f, 0.79f), Shader.TileMode.CLAMP
    )

    override fun onDraw(canvas: Canvas?) {
        canvas ?: return
        drawCircle(canvas = canvas)
        drawShadow(canvas = canvas)
        drawBorderWithShadow(canvas = canvas)
    }

    private fun drawCircle(canvas: Canvas) {
        canvas.drawCircle(circleCenterX, circleCenterY, circleRadius, paintFirstCircle)
    }

    private fun drawShadow(canvas: Canvas) {
        canvas.drawCircle(circleCenterX, circleCenterY, circleRadius, paintShadow)
    }

    private fun drawBorderWithShadow(canvas: Canvas) {
        canvas.drawCircle(circleCenterY, circleCenterY, circleRadius, paintBorderCircle)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val desiredWidth = calculateWidth(widthMeasureSpec)
        val desiredHeight = calculateHeight(heightMeasureSpec)

        setMeasuredDimension(
            resolveSize(desiredWidth, widthMeasureSpec),
            resolveSize(desiredHeight, heightMeasureSpec)
        )
    }

    private fun calculateWidth(spec: Int): Int {
        val measuredWidth = MeasureSpec.getSize(spec)
        return measuredWidth + paddingLeft + paddingRight
    }

    private fun calculateHeight(spec: Int): Int {
        val measuredHeight = MeasureSpec.getSize(spec)
        return measuredHeight + paddingTop + paddingBottom
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        setup()
    }

    private fun setup() {
        circleCenterX = (width / 2f)
        circleCenterY = (height / 2f)
        circleRadius = (width / 2f) - paddingTop - paddingLeft

        endX = width.toFloat()
        endY = height.toFloat()

        paintFirstCircle.shader = createCircleShader()
        paintBorderCircle.shader = createBorderShader()
        paintBorderCircle.setShadowLayer(shadowRadius, 0f, 0f, yellow)
    }
}
