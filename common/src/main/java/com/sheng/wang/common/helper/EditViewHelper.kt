package com.sheng.wang.common.helper

import android.text.Selection
import android.widget.EditText

/**
 * move gb to end
 */
fun EditText?.moveGB2End() {
    val text = this?.editableText
    Selection.setSelection(text, text?.length ?: 0)
}