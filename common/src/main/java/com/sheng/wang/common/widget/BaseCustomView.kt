package com.sheng.wang.common.widget

import android.content.Context
import android.util.AttributeSet
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import androidx.annotation.AnimRes
import androidx.core.view.isVisible


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
        get() = isVisible

    override fun initListener() {
    }


    open fun resume() {
    }


    open fun pause() {
    }


    open fun destroy() {
    }

    /**
     * 播放动画
     */
    open fun startAnim(@AnimRes animId: Int) {
        val showAnim = AnimationUtils.loadAnimation(context, animId)
        startAnimation(showAnim)
    }

    init {
        this.initView(attrs)
        this.initListener()
    }

}