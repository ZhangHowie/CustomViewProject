package com.example.drawtext.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.howie.customviewproject.util.toPx

class TextMeasuredView(ctx: Context, attrs: AttributeSet?) : View(ctx, attrs) {

    private val radius = 150.toPx()

    private val circleWith = 20.toPx()

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val textSize = 80.toPx()

    private val text = "aabb"

    private val textBounds: Rect = Rect()


    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        paint.style = Paint.Style.STROKE



        paint.color = Color.GRAY
        paint.strokeWidth = circleWith
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, paint)


        paint.color = Color.parseColor("#FF9540")
        paint.strokeCap = Paint.Cap.ROUND
        canvas.drawArc(
            (width / 2).toFloat() - radius, (height / 2).toFloat() - radius,
            (width / 2).toFloat() + radius, (height / 2).toFloat() + radius,
            -90f, 240f, false, paint
        )

        //文字绝对居中, 适合固定文字
//        drawTextAbsoluteCenter(canvas)

        //文字相对居中, 适合可变字体
        drawTextRelativeCenter(canvas)

//        drawTextInViewTopLeft(canvas)

    }

    /**
     * 重置文字画笔
     */
    private fun resetTextPaint() {

        paint.reset()
    }

    /**
     * Way 1, 绝对居中法
     *使用TextBounds 适合文字固定算出来的中间位置，文字不会改变的场景
     *而如果文字是动态改变的，文字的中间位置就会根据文字的不同进行上下浮动，显然不符合我们的需求。
     */
    private fun drawTextAbsoluteCenter(canvas: Canvas) {

        resetTextPaint()

        paint.textSize = textSize
        paint.textAlign = Paint.Align.CENTER
        paint.getTextBounds(text, 0, text.length, textBounds)
        val differ = (textBounds.bottom + textBounds.top) / 2f
        canvas.drawText(text, width / 2f, height / 2f - differ, paint)
    }


    /**
     * 相对居中法
     * 使用相对来说固定的字体 Ascent, Decent 值, 来相对的将文字置于居中位置
     */
    private fun drawTextRelativeCenter(canvas: Canvas) {
        resetTextPaint()

        paint.textSize = textSize
        paint.textAlign = Paint.Align.CENTER
        val fontMetrics = paint.fontMetrics
        val centDiffer = (fontMetrics.descent + fontMetrics.ascent) / 2f
        canvas.drawText(text, width / 2f, height / 2f - centDiffer, paint)

    }


    /**
     * 在View的左上角画文字
     */
    private fun drawTextInViewTopLeft(canvas: Canvas) {
        resetTextPaint()
        paint.textSize = 150.toPx()
        paint.textAlign = Paint.Align.LEFT
        val fontMetrics = paint.fontMetrics
        paint.getTextBounds(text, 0, text.length, textBounds)


        canvas.drawText(text, -textBounds.left.toFloat(), 0f - textBounds.top.toFloat(), paint)
        canvas.drawText(text, 0f, 0f - textBounds.top.toFloat() + paint.fontSpacing, paint)
//        canvas.drawText(text, 0f, 0f - textBounds.top.toFloat(), paint)
//        canvas.drawText(text, 0f, - fontMetrics.ascent, paint)
    }
}