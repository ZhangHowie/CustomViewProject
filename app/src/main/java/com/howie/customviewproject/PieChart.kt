package com.howie.customviewproject

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.howie.customviewproject.util.toPx
import kotlin.math.cos
import kotlin.math.sin

class PieChart(ctx: Context, attrs: AttributeSet) : View(ctx, attrs) {

    private val paint: Paint = Paint(ANTI_ALIAS_FLAG)

    private val angles = arrayOf(70f, 40f, 30f, 50f, 10f, 100f, 60f)

    private val colors = arrayOf(
        Color.BLUE,
        Color.CYAN,
        Color.DKGRAY,
        Color.RED,
        Color.BLACK,
        Color.GRAY,
        Color.GREEN
    )

    private val radius = 100.toPx()

    private val swipeLength = 30.toPx()

    private val swipeIndex = 6

    private lateinit var rect: RectF

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        rect = RectF(
            width / 2 - radius, height / 2 - radius,
            width / 2 + radius, height / 2 + radius
        )

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.style = Paint.Style.FILL
        var startAngle = 0f
        angles.forEachIndexed { i, angle ->
            if (i == swipeIndex) {
                val radians = Math.toRadians((startAngle + angle / 2).toDouble())
                canvas.save()
                canvas.translate((cos(radians) * swipeLength).toFloat(), (sin(radians) * swipeLength).toFloat())
            }

            paint.color = colors[i]
            canvas.drawArc(rect, startAngle, angle, true, paint)
            startAngle += angle

            if (i == swipeIndex) {
                canvas.restore()
            }
        }
    }
}