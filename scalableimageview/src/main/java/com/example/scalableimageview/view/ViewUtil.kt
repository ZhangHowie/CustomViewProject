package com.example.scalableimageview.view

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.scalableimageview.R

fun Int.toDp(): Float = (this / Resources.getSystem().displayMetrics.density)
fun Int.toPx(): Float = (this * Resources.getSystem().displayMetrics.density)


fun getAvatarBitmap(width: Float, resources: Resources): Bitmap {
    val options = BitmapFactory.Options()

    options.inJustDecodeBounds = true
    BitmapFactory.decodeResource(resources, R.drawable.howie_avatar, options)
    options.inJustDecodeBounds = false
    options.inDensity = options.outWidth
    options.inTargetDensity = width.toInt()

    return BitmapFactory.decodeResource(resources, R.drawable.howie_avatar, options)

}