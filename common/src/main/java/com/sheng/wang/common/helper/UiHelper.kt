package com.sheng.wang.common.helper

import android.util.Log
import android.view.Window
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsCompat.Type
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.FragmentActivity

/**
 * 开启安卓全屏适配
 */
fun FragmentActivity.openWindowInsets() {
    WindowCompat.setDecorFitsSystemWindows(window, false)//开启沉淀式 false开启，true关闭
}

/**
 * open full window
 */
fun Window.openWindowInsets() {
    WindowCompat.setDecorFitsSystemWindows(this, false)//开启沉淀式 false开启，true关闭
}

/**
 * 隐藏底部导航栏
 */
fun FragmentActivity.hideNavigationBars() {
    WindowCompat.getInsetsController(window, window.decorView).apply {
        hide(Type.navigationBars())
        systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}

/**
 * 设置顶部状态栏颜色
 * @param isDark true黑色，false白色
 */
fun FragmentActivity.setStatusBarsColor(isDark: Boolean = false) {
    WindowCompat.getInsetsController(window, window.decorView).apply {
        isAppearanceLightStatusBars = isDark
    }
}

/**
 * 设置导航栏图标颜色
 * @param isDark true黑色，false白色
 */
fun FragmentActivity.setNavigationBarsColor(isDark: Boolean = false) {
    WindowCompat.getInsetsController(window, window.decorView).apply {
        isAppearanceLightNavigationBars = isDark
    }
}

/**
 * 设置顶部状态栏颜色
 * @param isDark true黑色，false白色
 */
fun Window?.setStatusBarsColor(isDark: Boolean = false) {
    this?.let {
        WindowCompat.getInsetsController(it, it.decorView).apply {
            isAppearanceLightStatusBars = isDark
        }
    }

}

/**
 * 设置导航栏图标颜色
 * @param isDark true黑色，false白色
 */
fun Window?.setNavigationBarsColor(isDark: Boolean = false) {
    this?.let {
        WindowCompat.getInsetsController(it, it.decorView).apply {
            isAppearanceLightNavigationBars = isDark
        }
    }
}

/**
 * 获取页面状态栏，导航栏高度
 * @param bloc 返回状态栏，导航栏高度
 */
fun FragmentActivity.getSystemBarsHeight(bloc: (Int, Int) -> Unit) {
    window.decorView.setOnApplyWindowInsetsListener { v, insets ->
        ViewCompat.getRootWindowInsets(v)?.let {
            val statusBar = it.getInsets(Type.statusBars()).top
            val navigationBar = it.getInsets(Type.navigationBars()).bottom
            Log.d("App log", "fragmentActivity height getSystemBarsHeight: $statusBar |$navigationBar")
            bloc(statusBar, navigationBar)
        } ?: run {
            bloc(0, 0)
        }
        insets
    }
}

/**
 * 获取状态栏高度-需要再activity渲染成功后调用
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
 * 获取导航栏高度-需要再activity渲染成功后调用
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
 * keyboard chang listener
 * @param bloc true open ,false close
 */
fun FragmentActivity.setKeyboardListener(bloc: Boolean.() -> Unit) {
    window.decorView.setOnApplyWindowInsetsListener { v, insets ->
        ViewCompat.getRootWindowInsets(v)?.let {
            val keyboardHeight = it.getInsets(WindowInsetsCompat.Type.ime()).bottom
            bloc(keyboardHeight > 0)
        }
        insets
    }
}

/**
 * keyboard is show
 */
fun FragmentActivity.isShowKeyboard(): Boolean {
    ViewCompat.getRootWindowInsets(this.window.decorView)?.let {
        val keyboardHeight = it.getInsets(WindowInsetsCompat.Type.ime()).bottom
        return keyboardHeight > 0
    }
    return false
}