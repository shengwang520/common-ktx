package com.sheng.wang.common.widget

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.card.MaterialCardView

class HVSameLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : MaterialCardView(context, attrs) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}