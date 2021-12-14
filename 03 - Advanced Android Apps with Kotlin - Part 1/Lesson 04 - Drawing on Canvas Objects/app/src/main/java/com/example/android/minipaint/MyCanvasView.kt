package com.example.android.minipaint

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.core.content.res.ResourcesCompat

//https://developer.android.com/reference/android/graphics/Canvas.html
//https://developer.android.com/reference/android/graphics/Paint.Style.html
//https://developer.android.com/reference/android/graphics/Paint.Join.html
//https://developer.android.com/reference/android/graphics/Paint.Cap.html

//https://developer.android.com/reference/kotlin/android/graphics/Path.html
//https://developer.android.com/reference/kotlin/android/view/MotionEvent.html
//https://developer.android.com/reference/kotlin/android/view/ViewConfiguration.html#getScaledTouchSlop%28%29

private const val STROKE_WIDTH = 12f // has to be float

class MyCanvasView(context: Context) : View(context) {

    // old
//    private lateinit var extraCanvas: Canvas
//    private lateinit var extraBitmap: Bitmap

    // Path representing the drawing so far
    private val drawing = Path()

    // Path representing what's currently being drawn
    private val curPath = Path()

    private val backgroundColor = ResourcesCompat.getColor(resources, R.color.colorBackground, null)

    private val drawColor = ResourcesCompat.getColor(resources, R.color.colorPaint, null)

    // Set up the paint with which to draw.
    private val paint = Paint().apply {
        color = drawColor
        // Smooths out edges of what is drawn without affecting shape.
        isAntiAlias = true
        // Dithering affects how colors with higher-precision than the device are down-sampled.
        isDither = true
        style = Paint.Style.STROKE // default: FILL
        strokeJoin = Paint.Join.ROUND // default: MITER
        strokeCap = Paint.Cap.ROUND // default: BUTT
        strokeWidth = STROKE_WIDTH // default: Hairline-width (really thin)
    }

    // old
//    private var path = Path()

    private var motionTouchEventX = 0f
    private var motionTouchEventY = 0f

    private var currentX = 0f
    private var currentY = 0f

    // don't need to draw EVERY pixel - can interpolate
    private val touchTolerance = ViewConfiguration.get(context).scaledTouchSlop

    // frame for draw area
    private lateinit var frame: Rect

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)

        // old
//        if (::extraBitmap.isInitialized) extraBitmap.recycle()
//        // if not recycled would be a memory leak, so recycle before creating a new one
//        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//        extraCanvas = Canvas(extraBitmap)
//        extraCanvas.drawColor(backgroundColor)

        // Calculate a rectangular frame around the picture.
        val inset = 40
        frame = Rect(inset, inset, width - inset, height - inset)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // old
//        canvas.drawBitmap(extraBitmap, 0f, 0f, null)
//
//        // Draw a frame around the canvas.
//        canvas.drawRect(frame, paint)

        canvas.drawColor(backgroundColor)

        // Draw the drawing so far
        canvas.drawPath(drawing, paint)
        // Draw any current squiggle
        canvas.drawPath(curPath, paint)
        // Draw a frame around the canvas
        canvas.drawRect(frame, paint)
    }

    private fun touchStart() {
        // old
//        path.reset()
        curPath.moveTo(motionTouchEventX, motionTouchEventY)
        currentX = motionTouchEventX
        currentY = motionTouchEventY
    }

    private fun touchMove() {
        val dx = Math.abs(motionTouchEventX - currentX)
        val dy = Math.abs(motionTouchEventY - currentY)
        if (dx >= touchTolerance || dy >= touchTolerance) {
            // QuadTo() adds a quadratic bezier from the last point,
            // approaching control point (x1,y1), and ending at (x2,y2).
            // quadTo is smoother line without corners (instead of lineTo)
            curPath.quadTo(
                currentX,
                currentY,
                (motionTouchEventX + currentX) / 2,
                (motionTouchEventY + currentY) / 2
            )
            currentX = motionTouchEventX
            currentY = motionTouchEventY
            // the drawPath call was old when the Path variables were not being used
            // Draw the path in the extra bitmap to cache it.
//            extraCanvas.drawPath(path, paint)
        }
        invalidate()
    }

    private fun touchUp() {
        // old
//        // Reset the path so it doesn't get drawn again.
//        path.reset()

        // Add the current path to the drawing so far
        drawing.addPath(curPath)
        // Rewind the current path for the next touch
        curPath.reset()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        motionTouchEventX = event.x
        motionTouchEventY = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchStart()
            MotionEvent.ACTION_MOVE -> touchMove()
            MotionEvent.ACTION_UP -> touchUp()
        }
        return true
    }

}