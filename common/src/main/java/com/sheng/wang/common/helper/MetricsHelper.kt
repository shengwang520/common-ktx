package com.sheng.wang.common.helper

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue

private fun Context.getDisplayMetrics(): DisplayMetrics {
    return resources.displayMetrics
}

fun Context?.dp2px(dp: Float): Int {
    return this?.let {
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getDisplayMetrics()).toInt()
    } ?: dp.toInt()
}

fun Context.getWidth(offset: Float = 0f, count: Int = 1): Int {
    return (getDisplayMetrics().widthPixels - dp2px(offset)) / count
}

fun Context.getHeight(): Int {
    return getDisplayMetrics().heightPixels
}

/**
 * width to height
 * @param scale w/h
 */
fun Int.getHeight(scale: Float): Int {
    return (this / scale).toInt()
}