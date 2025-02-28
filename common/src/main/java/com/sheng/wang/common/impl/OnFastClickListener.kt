package com.sheng.wang.common.impl

import android.view.View

/**
 * click interval default 1s
 */
abstract class OnFastClickListener @JvmOverloads constructor(private var interval: Long = 1000L) : View.OnClickListener {
    private var mLastClickTime: Long = 0

    override fun onClick(v: View) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - mLastClickTime > interval) {
            onClickView(v)
            mLastClickTime = currentTime
        }
    }

    protected abstract fun onClickView(v: View)
}