package com.sheng.wang.common.widget

import android.util.AttributeSet


interface ICustomView {

    fun initView(attrs: AttributeSet?)

    fun show()

    fun hide()

    fun onBack(): Boolean

    val isShowing: Boolean

    fun initListener()
}