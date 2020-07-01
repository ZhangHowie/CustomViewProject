package com.howie.customviewproject

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.howie.customviewproject.util.toPx
import kotlin.math.cos
import kotlin.math.sin

/**
 * 仪表盘自定义View
 */
class DashBoardView(ctx: Context, attrs: AttributeSet): View(ctx, attrs) {

    //开启抗锯齿
    val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    val RADIUS: Float = 150.toPx()

    val OPEN_ANGLE = 120

    val path: Path = Path()
    var pathLength = 0f


    var oval: RectF

    val STROKE_WIDTH = 2.toPx()

    val line_length = 100.toPx()

    val LINE_NUM = 3

    lateinit var dashPathEffect: PathEffect

    val DASH_COUNT = 20

    var pathMeasure = PathMeasure()

    init {
        paint.strokeWidth = STROKE_WIDTH
        oval = RectF(width / 2 - RADIUS, height / 2 - RADIUS, width /2 + RADIUS, height /2 + RADIUS)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        path.reset()
        oval = RectF(w /2 - RADIUS, h / 2 - RADIUS, w /2 + RADIUS, h /2 + RADIUS)

        path.fillType = Path.FillType.EVEN_ODD
        path.addArc(oval, 90  + (OPEN_ANGLE / 2).toFloat(), (360 - OPEN_ANGLE).toFloat())

        //添加PathEffect
        val dashPath = Path()
        dashPath.addRect(0f, 0f, 1.toPx(), 10.toPx(), Path.Direction.CCW)
        pathMeasure.setPath(path, false)
        pathLength = pathMeasure.length
        dashPathEffect = PathDashPathEffect(dashPath, (pathLength - 1.toPx())/DASH_COUNT, 0f,
            PathDashPathEffect.Style.ROTATE)


    }



    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.style = Paint.Style.STROKE
        //画一个弧
        canvas.drawPath(path, paint)

        //画刻度
        paint.pathEffect = dashPathEffect
        canvas.drawPath(path, paint)
        paint.pathEffect = null

        //画指针
        val oneToSweep = (360 - OPEN_ANGLE) / DASH_COUNT
        val x = Math.toRadians((OPEN_ANGLE / 2 + 90 + oneToSweep * LINE_NUM).toDouble())
        canvas.drawLine((width / 2 ).toFloat(), (height / 2).toFloat(),
            (width / 2 + line_length * cos(x)).toFloat(),
            (height / 2 + line_length * sin(x)).toFloat(), paint)
    }
}