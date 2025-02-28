package com.sheng.wang.common.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout


abstract class BaseCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), ICustomView {
    abstract override fun initView(attrs: AttributeSet?)
    override fun show() {
        visibility = VISIBLE
    }

    override fun hide() {
        visibility = GONE
    }

    override fun onBack(): Boolean {
        return false
    }

    override val isShowing: Boolean
        get() = visibility == VISIBLE

    override fun initListener() {
    }


    open fun resume() {
    }


    open fun pause() {
    }


    open fun destroy() {
    }

    init {
        this.initView(attrs)
        this.initListener()
    }

}