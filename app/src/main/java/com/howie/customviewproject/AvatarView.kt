package com.howie.customviewproject

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.howie.customviewproject.util.toPx

class AvatarView(ctx: Context, attrs: AttributeSet) : View(ctx, attrs) {



    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val width = 300.toPx()

    private val padding = 30.toPx()

    private var bitmap = getAvatarBitmap(width.toInt())

    //模式
    private val xFerMode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

    private val rectF: RectF = RectF(padding, padding, padding + width, padding + width)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.color = Color.GRAY

        //绘制一个外围圆环
        canvas.drawCircle(padding + width / 2, padding + width / 2, width / 2 + 5.toPx(), paint)

        //先做一个离屏缓冲
        val count = canvas.saveLayer(rectF, paint)
        canvas.drawCircle(padding + width / 2, padding + width / 2, width / 2, paint)
        //把两个绘制的图形进行结合
        paint.xfermode = xFerMode
        canvas.drawBitmap(bitmap, padding, padding, paint)

        //恢复画笔的默认xfermode
        paint.xfermode = null

        //恢复画布的离屏缓冲
        canvas.restoreToCount(count)
    }

    private fun getAvatarBitmap(width: Int): Bitmap {
        val options = BitmapFactory.Options()

        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.howie_avatar, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width

        return BitmapFactory.decodeResource(resources, R.drawable.howie_avatar, options)

    }
}