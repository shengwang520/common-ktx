package com.sheng.wang.common.helper

import android.content.Context
import android.text.Selection
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * move gb to end
 */
fun EditText?.moveGB2End() {
    val text = this?.editableText
    Selection.setSelection(text, text?.length ?: 0)
}

/**
 * show keyboard
 */
fun EditText?.showKeyboard() {
    this?.let {
        it.isFocusable = true
        it.isFocusableInTouchMode = true
        it.requestFocus()
        val imm = (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        imm.showSoftInput(it, 0)
    }
}

/**
 * hide keyboard
 */
fun EditText?.hideKeyboard() {
    this?.let {
        val imm = (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}