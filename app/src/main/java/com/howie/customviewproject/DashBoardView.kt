package com.howie.customviewproject

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.howie.customviewproject.util.toPx

/**
 * 仪表盘自定义View
 */
class DashBoardView(ctx: Context, attrs: AttributeSet): View(ctx, attrs) {

    //开启抗锯齿
    val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG);

    val RADIUS: Float = 150.toPx()

    val path: Path = Path()
    var pathLength = 0f


    lateinit var oval: RectF

    val STROKE_WIDTH = 2.toPx()

    lateinit var dashPathEffect: PathEffect

    val DASH_COUNT = 15

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
        path.addArc(oval, 60 + 90.toFloat(), 360 - 60 - 60.toFloat())

        //添加PathEffect
        val dashPath = Path()
        dashPath.addRect(0f, 0f, 1.toPx(), 10.toPx(), Path.Direction.CCW)
        pathMeasure.setPath(path, false)
        pathLength = pathMeasure.length
        dashPathEffect = PathDashPathEffect(dashPath, (pathLength + (pathLength/DASH_COUNT))/ DASH_COUNT, 0f, PathDashPathEffect.Style.ROTATE)
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
    }
}