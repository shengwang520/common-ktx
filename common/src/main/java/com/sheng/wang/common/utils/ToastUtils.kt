package com.sheng.wang.common.utils

import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.widget.Toast

/**
 * toast
 */
object ToastUtils {
    /**
     * show
     */
    fun show(context: Context?, resId: Int) {
        if (context == null) return
        val toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT)
        toast.setText(resId)
        toast.show()
    }

    /**
     * show
     */
    fun show(context: Context?, msg: String?, isCenter: Boolean = false) {
        if (context == null) return
        if (TextUtils.isEmpty(msg)) return
        val toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
        toast.setText(msg)
        if (isCenter) {
            toast.setGravity(Gravity.CENTER, 0, 0)
        }
        toast.show()
    }
}