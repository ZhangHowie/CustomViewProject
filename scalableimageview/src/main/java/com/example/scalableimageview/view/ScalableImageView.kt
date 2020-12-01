package com.example.scalableimageview.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat

class ScalableImageView(context: Context?, attrs: AttributeSet?) :
    View(context, attrs), GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    var bitmap: Bitmap

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var offsetX: Float = 0f
    private var offsetY: Float = 0f

    private var smallScala: Float = 0f
    private var bigScale = 0f
    private var currentScale = 0f

    private var isBig: Boolean = false

    private var gestureDetectorCompat: GestureDetectorCompat
    companion object {
        private const val TAG = "ScalableImageView"
        private val IMAGE_WIDTH = 300.toPx()
    }

    init {
        bitmap = getAvatarBitmap(IMAGE_WIDTH, resources)
        gestureDetectorCompat = GestureDetectorCompat(context, this)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        offsetX = (width - bitmap.width) / 2f
        offsetY = (height - bitmap.height) / 2f

        smallScala = Math.min(width / bitmap.width.toFloat(), height / bitmap.height.toFloat())
        bigScale = Math.max(width / bitmap.width.toFloat(), height / bitmap.height.toFloat())
        if (isBig) {
            currentScale = bigScale
        } else {
            currentScale = smallScala
        }
        Log.d(TAG, "smallScale: $smallScala")
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.scale(currentScale, currentScale, width / 2f, height / 2f)
        canvas.drawBitmap(bitmap, offsetX, offsetY, paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetectorCompat.onTouchEvent(event)
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }

    override fun onShowPress(e: MotionEvent?) {

    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent?) {

    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return false
    }

    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        return false
    }

    override fun onDoubleTap(e: MotionEvent?): Boolean {
        isBig = !isBig
        if (isBig) {
            currentScale = bigScale
        } else {
            currentScale = smallScala
        }
        invalidate()
        return false
    }

    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        return false
    }


}