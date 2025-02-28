package com.sheng.wang.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * Fragment base
 */
abstract class BaseFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
        initViewModel()
    }

    /**
     * return view
     */
    abstract fun createView(inflater: LayoutInflater, container: ViewGroup?): View?

    /**
     * init view
     */
    protected abstract fun initView()

    /**
     * init listener
     */
    protected open fun initListener() {}

    /**
     * init viewModel
     */
    protected open fun initViewModel() {}

    companion object {
        const val DATA_KEY = "data_page"
    }
}