package com.example.drawtext.ui.custom

import android.content.Context
import android.content.res.Resources
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.example.drawtext.ui.custom.MultiLinesView.Companion.getAvatarBitmap
import com.howie.customviewproject.util.toPx

class CanvasClipView(ctx: Context, attrs: AttributeSet?) : View(ctx, attrs) {


    private val bitmapWidth = 200.toPx()
    private val drawOffset = 100.toPx()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val camera = Camera()

    private val bitmap = getAvatarBitmap(bitmapWidth, resources)

    val clipRectF = RectF(
        drawOffset,
        drawOffset,
        drawOffset + bitmapWidth,
        drawOffset + bitmapWidth / 2
    )

    init {
        camera.setLocation(0f, 0f, -3 * Resources.getSystem().displayMetrics.density)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

//        drawWIthClip(canvas)
//        drawWithCamera(canvas)
//        drawWithCameraClip(canvas)
//        firstClipAndUseCamera(canvas)
        drawWithCameraClipInclined(canvas)

    }

    private fun drawWIthClip(canvas: Canvas) {
        canvas.save()

        canvas.clipRect(clipRectF)
        canvas.drawBitmap(bitmap, drawOffset, drawOffset, paint)
        canvas.restore()
    }


    /**
     * 通过Camera绘制直放大的图形
     */
    private fun drawWithCamera(canvas: Canvas) {
        canvas.save()
        camera.rotateX(30f)
        canvas.translate(drawOffset + bitmapWidth / 2, drawOffset + bitmapWidth / 2)
        camera.applyToCanvas(canvas)
        canvas.translate(-(drawOffset + bitmapWidth / 2), -(drawOffset + bitmapWidth / 2))
        canvas.drawBitmap(bitmap, drawOffset, drawOffset, paint)
        canvas.restore()
    }

    /**
     * 通过Camera绘制直一半放大的图形
     */
    private fun drawWithCameraClip(canvas: Canvas) {

        canvas.save()
        canvas.translate(drawOffset + bitmapWidth / 2, drawOffset + bitmapWidth / 2)
        canvas.clipRect(-bitmapWidth / 2, -bitmapWidth / 2, bitmapWidth / 2, 0f)
        canvas.translate(-(drawOffset + bitmapWidth / 2), -(drawOffset + bitmapWidth / 2))
        canvas.drawBitmap(bitmap, drawOffset, drawOffset, paint)
        canvas.restore()

        canvas.save()
        camera.rotateX(30f)
        canvas.translate(drawOffset + bitmapWidth / 2, drawOffset + bitmapWidth / 2)
        camera.applyToCanvas(canvas)
        canvas.clipRect(-bitmapWidth / 2, 0f, bitmapWidth / 2, bitmapWidth / 2)
        canvas.translate(-(drawOffset + bitmapWidth / 2), -(drawOffset + bitmapWidth / 2))
        canvas.drawBitmap(bitmap, drawOffset, drawOffset, paint)
        canvas.restore()

    }

    /**
     * 通过Camera绘制直一半放大的图形
     * 先裁切，再去做翻页效果
     */
    private fun firstClipAndUseCamera(canvas: Canvas) {
        canvas.save()
        canvas.clipRect(clipRectF)
        canvas.drawBitmap(bitmap, drawOffset, drawOffset, paint)
        canvas.restore()

        canvas.save()
        camera.rotateX(30f)
        canvas.translate(drawOffset + bitmapWidth / 2, drawOffset + bitmapWidth / 2)
        camera.applyToCanvas(canvas)
        canvas.translate(-(drawOffset + bitmapWidth / 2), -(drawOffset + bitmapWidth / 2))
        canvas.clipRect(
            drawOffset,
            drawOffset + bitmapWidth / 2,
            drawOffset + bitmapWidth,
            drawOffset + bitmapWidth
        )
        canvas.drawBitmap(bitmap, drawOffset, drawOffset, paint)
        canvas.restore()

    }

    /**
     * 通过Camera绘制直放大的图形
     */
    private fun drawWithCameraClipInclined(canvas: Canvas) {
        canvas.save()
        canvas.translate(drawOffset + bitmapWidth / 2, drawOffset + bitmapWidth / 2)
        canvas.rotate(-30f)

        //因为旋转了30度，所以我们裁切的范围需要更大，否则就会有一些本应该在的内容被裁掉
        canvas.clipRect(-bitmapWidth, -bitmapWidth, bitmapWidth, 0f)

        canvas.rotate(30f)
        canvas.translate(-(drawOffset + bitmapWidth / 2), -(drawOffset + bitmapWidth / 2))
        canvas.drawBitmap(bitmap, drawOffset, drawOffset, paint)
        canvas.restore()

        canvas.save()
        canvas.translate(drawOffset + bitmapWidth / 2, drawOffset + bitmapWidth / 2)
        canvas.rotate(-30f)
        camera.rotateX(30f)
        camera.applyToCanvas(canvas)

        //因为旋转了30度，所以我们裁切的范围需要更大，否则就会有一些本应该在的内容被裁掉
        canvas.clipRect(-bitmapWidth, 0f, bitmapWidth, bitmapWidth)
        canvas.rotate(30f)
        canvas.translate(-(drawOffset + bitmapWidth / 2), -(drawOffset + bitmapWidth / 2))
        canvas.drawBitmap(bitmap, drawOffset, drawOffset, paint)
        canvas.restore()
    }
}