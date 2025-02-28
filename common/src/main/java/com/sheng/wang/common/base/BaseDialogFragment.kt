package com.sheng.wang.common.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

/**
 * dialog fragment base
 */
abstract class BaseDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initWindow()
        initView()
        initListener()
        initViewModel()
    }

    /**
     * 返回界面布局id
     */
    abstract fun createView(inflater: LayoutInflater, container: ViewGroup?): View?

    /**
     * 初始化window
     */
    protected open fun initWindow() {
        //初始化window相关表现
        val window = dialog?.window
        //设备背景为透明（默认白色）
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //设置window宽高(单位px)
        //window?.attributes?.width = 700
        //window?.attributes?.height = 350
    }

    /**
     * 初始化布局
     */
    protected open fun initView() {}

    /**
     * 初始化事件
     */
    protected open fun initListener() {}

    /**
     * 初始化viewModel
     */
    protected open fun initViewModel() {}

    /**
     * 显示唯一布局
     * @param isOnly 是否显示唯一
     */
    open fun showNowOnly(manager: FragmentManager, tag: String?, isOnly: Boolean = true) {
        if (isOnly) {
            val fragment = manager.findFragmentByTag(tag)
            if (fragment == null || !fragment.isVisible) {
                showNow(manager, tag)
            }
        } else {
            showNow(manager, tag)
        }
    }

    override fun showNow(manager: FragmentManager, tag: String?) {
        try {
            super.showNow(manager, tag)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun dismiss() {
        try {
            super.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}