package com.example.android.customfancontroller

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

//https://android-developers.googleblog.com/2018/02/introducing-android-ktx-even-sweeter.html
//https://developer.android.com/reference/android/view/View.html
//https://developer.android.com/training/custom-views/index.html
//https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html
//https://android.github.io/android-ktx/core-ktx/index.html
//https://developer.android.com/kotlin/ktx

//https://developer.android.com/guide/topics/ui/custom-components.html#compound
//https://developer.android.com/guide/topics/ui/how-android-draws.html
//https://developer.android.com/reference/android/view/View.html#onMeasure%28int,%20int%29
//https://developer.android.com/reference/android/view/View.html#onSizeChanged%28int,%20int,%20int,%20int%29
//https://developer.android.com/reference/android/view/View.html#onDraw%28android.graphics.Canvas%29
//https://developer.android.com/reference/android/graphics/Canvas.html
//https://developer.android.com/reference/android/graphics/Paint.html
//https://developer.android.com/reference/android/graphics/Canvas.html#drawText%28char[],%20int,%20int,%20float,%20float,%20android.graphics.Paint%29
//https://developer.android.com/reference/android/graphics/Paint.html#setTypeface%28android.graphics.Typeface%29
//https://developer.android.com/reference/android/graphics/Paint.html#setColor%28int%29
//https://developer.android.com/reference/android/graphics/Canvas.html#drawRect%28android.graphics.Rect,%20android.graphics.Paint%29
//https://developer.android.com/reference/android/graphics/Canvas.html#drawOval%28android.graphics.RectF,%20android.graphics.Paint%29
//https://developer.android.com/reference/android/graphics/Canvas.html#drawArc%28android.graphics.RectF,%20float,%20float,%20boolean,%20android.graphics.Paint%29
//https://developer.android.com/reference/android/graphics/Canvas.html#drawBitmap%28android.graphics.Bitmap,%20android.graphics.Matrix,%20android.graphics.Paint%29
//https://developer.android.com/reference/android/graphics/Paint.html#setStyle%28android.graphics.Paint.Style%29
//https://developer.android.com/reference/android/view/View.html#invalidate%28%29

//https://developer.android.com/reference/android/graphics/PointF
//https://android.github.io/android-ktx/core-ktx/androidx.content/android.content.-context/index.html

//https://developer.android.com/reference/android/view/View.html#isClickable%28%29
//https://developer.android.com/reference/android/view/View.OnClickListener.html
//https://developer.android.com/reference/android/view/View#performClick%28%29

private enum class FanSpeed(val label: Int) {
    OFF(R.string.fan_off),
    LOW(R.string.fan_low),
    MEDIUM(R.string.fan_medium),
    HIGH(R.string.fan_high);

    fun next() = when (this) {
        OFF -> LOW
        LOW -> MEDIUM
        MEDIUM -> HIGH
        HIGH -> OFF
    }
}

private const val RADIUS_OFFSET_LABEL = 30
private const val RADIUS_OFFSET_INDICATOR = -35

class DialView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var fanSpeedLowColor = 0
    private var fanSpeedMediumColor = 0
    private var fanSeedMaxColor = 0

    private var radius = 0.0f                   // Radius of the circle.
    private var fanSpeed = FanSpeed.OFF         // The active selection.

    // position variable which will be used to draw label and indicator circle position
    private val pointPosition: PointF = PointF(0.0f, 0.0f)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        typeface = Typeface.create("", Typeface.BOLD)
    }

    init {
        isClickable = true

        // attribute retrieval
        context.withStyledAttributes(attrs, R.styleable.DialView) {
            fanSpeedLowColor = getColor(R.styleable.DialView_fanColor1, 0)
            fanSpeedMediumColor = getColor(R.styleable.DialView_fanColor2, 0)
            fanSeedMaxColor = getColor(R.styleable.DialView_fanColor3, 0)
        }
    }

    override fun performClick(): Boolean {
        if (super.performClick()) return true

        fanSpeed = fanSpeed.next()
        contentDescription = resources.getString(fanSpeed.label)

        invalidate()
        return true
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        radius = (min(width, height) / 2.0 * 0.8).toFloat()
    }

    private fun PointF.computeXYForSpeed(pos: FanSpeed, radius: Float) {
        // Angles are in radians.
        val startAngle = Math.PI * (9 / 8.0)
        val angle = startAngle + pos.ordinal * (Math.PI / 4)
        x = (radius * cos(angle)).toFloat() + width / 2
        y = (radius * sin(angle)).toFloat() + height / 2
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Set dial background color to based on attributes
        paint.color = when (fanSpeed) {
            FanSpeed.OFF -> Color.GRAY
            FanSpeed.LOW -> fanSpeedLowColor
            FanSpeed.MEDIUM -> fanSpeedMediumColor
            FanSpeed.HIGH -> fanSeedMaxColor
        } as Int

        // Draw the dial.
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, paint)

        // Draw the indicator circle.
        val markerRadius = radius + RADIUS_OFFSET_INDICATOR
        pointPosition.computeXYForSpeed(fanSpeed, markerRadius)
        paint.color = Color.BLACK
        canvas.drawCircle(pointPosition.x, pointPosition.y, radius / 12, paint)

        // Draw the text labels.
        val labelRadius = radius + RADIUS_OFFSET_LABEL
        for (i in FanSpeed.values()) {
            pointPosition.computeXYForSpeed(i, labelRadius)
            val label = resources.getString(i.label)
            canvas.drawText(label, pointPosition.x, pointPosition.y, paint)
        }
    }

}