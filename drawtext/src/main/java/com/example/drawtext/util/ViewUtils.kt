package com.howie.customviewproject.util

import android.content.res.Resources

fun Int.toDp(): Float = (this / Resources.getSystem().displayMetrics.density)
fun Int.toPx(): Float = (this * Resources.getSystem().displayMetrics.density)