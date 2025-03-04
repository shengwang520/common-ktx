package com.sheng.wang.common.helper

import android.graphics.Point
import android.util.Log
import android.view.Window
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat.Type
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData

/**
 * system bar height must use [registerSystemBarsHeight]
 * * [Point] x is statusBar height, y is navigationBar height
 * @see registerSystemBarsHeight
 */
val systemBarsHeight = MutableLiveData<Point>()

/**
 * open full window
 */
fun Window?.openWindowInsets() {
    this?.let {
        WindowCompat.setDecorFitsSystemWindows(it, false)//开启沉淀式 false开启，true关闭
    }
}

/**
 * open full window
 */
fun FragmentActivity?.openWindowInsets() {
    this?.window.openWindowInsets()
}

/**
 * open full window
 */
fun DialogFragment?.openWindowInsets() {
    this?.dialog?.window.openWindowInsets()
}

/**
 * set statusBars color
 * @param isDark true black，false white
 */
fun Window?.setStatusBarsColor(isDark: Boolean = false) {
    this?.let {
        WindowCompat.getInsetsController(it, it.decorView).apply {
            isAppearanceLightStatusBars = isDark
        }
    }
}

/**
 * set statusBars color
 * @param isDark true black，false white
 */
fun FragmentActivity?.setStatusBarsColor(isDark: Boolean = false) {
    this?.window.setStatusBarsColor(isDark)
}

/**
 * set statusBars color
 * @param isDark true black，false white
 */
fun DialogFragment?.setStatusBarsColor(isDark: Boolean = false) {
    this?.dialog?.window.setStatusBarsColor(isDark)
}

/**
 * set navigationBars color
 * @param isDark true black，false white
 */
fun Window?.setNavigationBarsColor(isDark: Boolean = false) {
    this?.let {
        WindowCompat.getInsetsController(it, it.decorView).apply {
            isAppearanceLightNavigationBars = isDark
        }
    }
}

/**
 * set navigationBars color
 * @param isDark true black，false white
 */
fun FragmentActivity?.setNavigationBarsColor(isDark: Boolean = false) {
    this?.window.setNavigationBarsColor(isDark)
}

/**
 * set navigationBars color
 * @param isDark true black，false white
 */
fun DialogFragment?.setNavigationBarsColor(isDark: Boolean = false) {
    this?.dialog?.window.setNavigationBarsColor(isDark)
}

/**
 * set systemBars color
 * @param isStatusBar statusBar true black，false white
 * @param isNavigationBar navigationBar true black，false white
 */
fun FragmentActivity?.setSystemBarsColor(isStatusBar: Boolean = false, isNavigationBar: Boolean = false) {
    this?.window.setStatusBarsColor(isStatusBar)
    this?.window.setNavigationBarsColor(isNavigationBar)
}

/**
 * set systemBars color
 * @param isStatusBar statusBar true black，false white
 * @param isNavigationBar navigationBar true black，false white
 */
fun DialogFragment?.setSystemBarsColor(isStatusBar: Boolean = false, isNavigationBar: Boolean = false) {
    this?.dialog?.window.setStatusBarsColor(isStatusBar)
    this?.dialog?.window.setNavigationBarsColor(isNavigationBar)
}

/**
 * register systemBars height
 */
fun FragmentActivity?.registerSystemBarsHeight() {
    getSystemBarsHeight { top, bottom ->
        systemBarsHeight.value = Point(top, bottom)
    }
}

/**
 * get statusBar height,navigationBar height
 * * activity only one times
 * @param bloc return statusBar，navigation height
 */
fun FragmentActivity?.getSystemBarsHeight(bloc: (Int, Int) -> Unit) {
    this?.window?.decorView?.setOnApplyWindowInsetsListener { v, insets ->
        ViewCompat.getRootWindowInsets(v)?.let {
            val statusBar = it.getInsets(Type.statusBars()).top
            val navigationBar = it.getInsets(Type.navigationBars()).bottom
            Log.d("App log", "fragmentActivity height getSystemBarsHeight: $statusBar |$navigationBar")
            systemBarsHeight.value = Point(statusBar, navigationBar)
            bloc(statusBar, navigationBar)
        } ?: run {
            bloc(0, 0)
        }
        insets
    }
}

/**
 * get statusBar height-when activity is show
 */
fun FragmentActivity?.getStatusBarHeight(): Int {
    this?.window?.let {
        ViewCompat.getRootWindowInsets(window.decorView)?.let {
            val navigationBar = it.getInsets(Type.statusBars()).bottom
            return navigationBar
        }
    }
    return 0
}

/**
 * get navigationBar height-when activity is show
 */
fun FragmentActivity?.getNavigationBarHeight(): Int {
    this?.window?.let {
        ViewCompat.getRootWindowInsets(window.decorView)?.let {
            val navigationBar = it.getInsets(Type.navigationBars()).bottom
            return navigationBar
        }
    }
    return 0
}

/**
 * hide navigationBar
 */
fun FragmentActivity?.hideNavigationBars() {
    this?.let {
        WindowCompat.getInsetsController(window, window.decorView).apply {
            hide(Type.navigationBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}

/**
 * keyboard chang listener
 * @param bloc true open ,false close
 */
fun FragmentActivity?.setKeyboardListener(bloc: Boolean.() -> Unit) {
    this?.window?.decorView?.setOnApplyWindowInsetsListener { v, insets ->
        ViewCompat.getRootWindowInsets(v)?.let {
            val keyboardHeight = it.getInsets(Type.ime()).bottom
            bloc(keyboardHeight > 0)
        }
        insets
    }
}

/**
 * keyboard is show
 */
fun FragmentActivity?.isShowKeyboard(): Boolean {
    this?.let {
        ViewCompat.getRootWindowInsets(window.decorView)?.let {
            val keyboardHeight = it.getInsets(Type.ime()).bottom
            return keyboardHeight > 0
        }
    }
    return false
}

/**
 * show keyboard
 */
fun FragmentActivity?.showKeyboard() {
    this?.let {
        WindowCompat.getInsetsController(window, window.decorView).apply {
            show(Type.ime())
        }
    }
}

/**
 * hide keyboard
 */
fun FragmentActivity?.hideKeyboard() {
    this?.let {
        WindowCompat.getInsetsController(window, window.decorView).apply {
            hide(Type.ime())
        }
    }
}