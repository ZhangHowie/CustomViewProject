package com.example.drawtext.ui.custom

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.example.drawtext.R
import com.howie.customviewproject.util.toPx

class MultiLinesView(ctx: Context, attrs: AttributeSet?) : View(ctx, attrs) {

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)

    private val loremIpsumText =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce sed ligula quam. Suspendisse in urna fermentum, ultricies nibh quis, aliquet massa. Mauris non neque non lorem rutrum facilisis in vel erat. Duis ullamcorper massa odio, et luctus libero gravida et. Fusce non malesuada eros, suscipit mattis leo. Praesent ultrices velit vitae finibus feugiat. Nunc quis eros nunc. Donec orci ligula, fringilla at finibus eu, viverra et ligula. Nam sodales ac ante sit amet gravida. Cras nec efficitur nibh, a lobortis velit. Nulla scelerisque orci vel consectetur ultrices. Lorem ipsum dolor sit amet, consectetur adipiscing elit.Integer dictum pellentesque purus quis egestas. Nunc fermentum felis vitae sem fringilla, eu lobortis elit egestas. Integer fringilla feugiat metus nec pulvinar. Praesent in nibh et sapien aliquet venenatis. Aliquam in nulla scelerisque, malesuada odio vitae, efficitur augue. Aenean vestibulum ipsum vitae libero dictum, eget dictum massa sollicitudin. Mauris commodo risus vel arcu tempor, non volutpat lectus pretium. Sed neque dolor, consequat egestas ante non, tempus cursus est. Morbi vel lacus hendrerit, fermentum dolor sed, porta magna. Nunc fringilla erat consectetur, lacinia purus non, luctus metus. Integer quam diam, lobortis in lectus eget, dignissim maximus sapien.Donec et tincidunt ipsum. Maecenas viverra nulla elit, vel tristique eros malesuada at. Etiam tristique justo nibh, eu tristique nisi venenatis vel. Fusce malesuada vel neque in placerat. Duis ut sapien metus. Curabitur at risus leo. Vivamus ac felis ac augue aliquet iaculis nec non ante. Morbi maximus orci arcu, nec elementum dolor dictum a. Nam laoreet nisl urna, quis euismod sapien dignissim a.Maecenas quis convallis lorem. Nam tristique laoreet lorem ut euismod. Proin ac turpis mauris. Aenean id nibh eu magna rhoncus pharetra nec vel nisl. Ut ullamcorper sit amet lorem at varius. Phasellus rhoncus neque erat, at molestie libero finibus in. Morbi ac nisi vitae enim ultricies fermentum consectetur vel sapien. Quisque eu semper velit. Morbi commodo nibh non dictum porttitor. Praesent eu lorem lacus. Sed volutpat placerat enim posuere iaculis. Proin interdum nisl eu dignissim varius. Proin efficitur consequat dapibus. Nullam consequat, dui ut malesuada vehicula, nisl ligula maximus risus, eget fermentum lorem leo vel dui. Morbi elementum condimentum ligula.Morbi lacinia lacinia tellus, vel tristique odio accumsan vitae. Quisque ultricies nisl a massa pellentesque tincidunt. Nulla vitae dignissim libero. Sed eu lacus sapien. Integer tincidunt elit quis diam consequat, in commodo tortor venenatis. Ut eget elementum nisi. Suspendisse suscipit vehicula egestas. Etiam tristique metus eget nisi tempus egestas ut a augue. Mauris malesuada molestie justo, id pretium turpis fringilla vel. Cras a ante quam. Integer finibus tortor eget purus sagittis, eu efficitur lorem varius. Proin eget egestas augue."


    private val measuredWidth = floatArrayOf(0f)

    private val bitmapOffset = 200.toPx()

    private val bitmapWidth = 200.toPx()

    private val bitmap = getAvatarBitmap(bitmapWidth, resources)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

//        normalDraw(canvas)
//        drawWithStaticLayout(canvas)
//        drawTextWithBreak(canvas)
        drawTextWithBitmap(canvas)


    }


    /**
     * 普通绘制,不会自动换行
     */
    private fun normalDraw(canvas: Canvas) {

        paint.reset()
        paint.color = Color.BLACK
        paint.textSize = 20.toPx()
        canvas.drawText(loremIpsumText, 0f, paint.fontSpacing, paint)
    }


    /**
     * 使用自动换行的StaticLayout绘制
     */
    private fun drawWithStaticLayout(canvas: Canvas) {
        textPaint.textSize = 15.toPx()

        val staticLayout =
            StaticLayout.Builder.obtain(loremIpsumText, 0, loremIpsumText.length, textPaint, width)
                .build()
        staticLayout.draw(canvas)
    }


    /**
     * 绘制自动换行的文字，使用breakText
     */
    private fun drawTextWithBreak(canvas: Canvas) {
        paint.reset()
        paint.color = Color.BLACK
        paint.textSize = 20.toPx()
        val length = loremIpsumText.length
        val measuredCount = paint.breakText(
            loremIpsumText,
            0,
            length,
            true,
            width.toFloat(),
            measuredWidth
        )
        canvas.drawText(loremIpsumText, 0, measuredCount, 0f, paint.fontSpacing, paint)

        var start = 0
        var startY = paint.fontSpacing
        while (start < length) {
            val count = paint.breakText(
                loremIpsumText,
                start, length,
                true,
                width.toFloat(),
                measuredWidth
            )
            canvas.drawText(loremIpsumText, start, start + count, 0f, startY, paint)
            start += count
            startY += paint.fontSpacing
        }

    }

    /**
     * 绘制图片和文字混合的View
     */
    private fun drawTextWithBitmap(canvas: Canvas) {
        paint.reset()
        canvas.drawBitmap(bitmap, (width - bitmapWidth).toFloat(), bitmapOffset, paint)


        paint.reset()
        paint.color = Color.BLACK
        paint.textSize = 20.toPx()
        val length = loremIpsumText.length
        val measuredCount = paint.breakText(
            loremIpsumText,
            0,
            length,
            true,
            width.toFloat(),
            measuredWidth
        )
        canvas.drawText(loremIpsumText, 0, measuredCount, 0f, paint.fontSpacing, paint)

        var start = 0
        var startY = paint.fontSpacing

        while (start < length) {
            //每次我测量需要断句的长度
            var willMeasureWidth = width.toFloat()

            //文字的高度位置
            val textTop = startY + paint.fontMetrics.top
            //文字的最低位置
            val textBottom = startY + paint.fontMetrics.bottom

            //文字的最高处是否在图片的范围内？其实也就是对应从下往上
            val textTopInBitmap = textTop > bitmapOffset && textTop < bitmapOffset + bitmapWidth

            //文字的最低处是否在图片的范围内？对应的是从上往下
            val textBottomInBitmap =
                textBottom > bitmapOffset && textBottom < bitmapOffset + bitmapWidth

            //如果最高处在范围内，或者最低处在范围内，都认为文字需要根据图片的宽度计算长度
            if (textTopInBitmap || textBottomInBitmap) {
                willMeasureWidth = width - bitmapWidth.toFloat()
            }

            //通过给定的长度来计算绘制文字的个数
            val count = paint.breakText(
                loremIpsumText,
                start, length,
                true, willMeasureWidth, measuredWidth
            )
            canvas.drawText(loremIpsumText, start, start + count, 0f, startY, paint)
            start += count
            startY += paint.fontSpacing
        }

    }

    companion object {
        fun getAvatarBitmap(width: Float, resources: Resources): Bitmap {
            val options = BitmapFactory.Options()

            options.inJustDecodeBounds = true
            BitmapFactory.decodeResource(resources, R.drawable.howie_avatar, options)
            options.inJustDecodeBounds = false
            options.inDensity = options.outWidth
            options.inTargetDensity = width.toInt()

            return BitmapFactory.decodeResource(resources, R.drawable.howie_avatar, options)

        }
    }


}