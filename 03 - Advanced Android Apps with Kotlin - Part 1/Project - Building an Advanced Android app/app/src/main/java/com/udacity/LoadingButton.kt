/*
 *
 * PROJECT LICENSE
 *
 * This project was submitted by Jason Jamieson as part of the Android Kotlin Developer Nanodegree At Udacity.
 *
 * As part of Udacity Honor code, your submissions must be of your own work.
 * Submission of this project will cause you to break the Udacity Honor Code
 * and possible suspension of your account.
 *
 * I, Jason Jamieson, the author of the project, allow you to check this code as reference, but if
 * used as submission, it's your responsibility if you are expelled.
 *
 * Copyright (c) 2021 Jason Jamieson
 *
 * Besides the above notice, the following license applies and this license notice
 * must be included in all works derived from this project.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.udacity

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    // size and progress variables
    private var widthSize = 0
    private var heightSize = 0
    private var progress = 0

    // color variables
    private var textColor = context.getColor(R.color.white)
    private var buttonColor = context.getColor(R.color.colorPrimary)
    private var buttonProgressColor = context.getColor(R.color.colorPrimaryDark)
    private var circleProgressColor = context.getColor(R.color.colorAccent)

    // bounds variable
    var rectBounds = Rect()

    // value animator variable
    private val valueAnimator = ValueAnimator()

    // button state variable
    private var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->
        if (new == ButtonState.Loading) {
            paint.color = context.getColor(R.color.colorPrimaryDark)
            valueAnimator.apply {
                setIntValues(0, 360)
                duration = 2000L
                addUpdateListener {
                    progress = animatedValue as Int
                    invalidate()
                }
                repeatCount = ValueAnimator.INFINITE
                start()
            }
        } else {
            paint.color = context.getColor(R.color.colorPrimary)
        }
        invalidate()
    }

    // paint variable
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        typeface = Typeface.create("", Typeface.BOLD)
    }

    init {
        // attribute retrieval
        context.withStyledAttributes(attrs, R.styleable.LoadingButton) {
            textColor = getColor(R.styleable.LoadingButton_textColor, textColor)
            buttonColor = getColor(R.styleable.LoadingButton_buttonColor, buttonColor)
            buttonProgressColor = getColor(R.styleable.LoadingButton_buttonProgressColor, buttonProgressColor)
            circleProgressColor = getColor(R.styleable.LoadingButton_circleProgressColor, circleProgressColor)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.let { canvas ->
            canvas.drawColor(context.getColor(R.color.colorPrimary))
            val text = if (buttonState == ButtonState.Loading) context.getString(R.string.button_loading) else context.getString(R.string.button_name)
            paint.getTextBounds(text, 0, text.length, rectBounds)

            if (buttonState == ButtonState.Loading) {
                paint.color = context.getColor(R.color.colorPrimaryDark)
                canvas.drawRect(0f, 0f, (progress * widthSize / 360).toFloat(), heightSize.toFloat(), paint)

                paint.color = context.getColor(R.color.colorAccent)
                val xArc = (widthSize / 2f) + (rectBounds.width() / 2)
                // paint.ascent and paint.decent from chatting around
                val yArc = (heightSize / 2f) + ((paint.ascent() + paint.descent()) / 2)
                canvas.drawArc(xArc, yArc, xArc + 40f, yArc + 40f, 0f, progress.toFloat(), true, paint)
            }

            paint.color = context.getColor(R.color.white)
            val xText = widthSize / 2f
            val yText = heightSize / 2f
            canvas.drawText(context.getString(R.string.button_name), xText, yText, paint)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
                MeasureSpec.getSize(w),
                heightMeasureSpec,
                0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }

    fun setCustomButtonState(state: ButtonState) {
        buttonState = state
    }

}