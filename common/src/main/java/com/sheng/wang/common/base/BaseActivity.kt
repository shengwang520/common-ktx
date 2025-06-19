package com.sheng.wang.common.base

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.sheng.wang.common.permission.registerPermissionLaunch
import com.sheng.wang.common.permission.registerPermissionsLaunch

/**
 * Activity base
 */
abstract class BaseActivity : AppCompatActivity() {
    private var currentFragment: Fragment? = null

    /**
     * get activity
     */
    val activity: BaseActivity
        get() = this

    /**
     * request one permission
     */
    val permissionLauncher = registerPermissionLaunch()

    /**
     * request more permission
     */
    val permissionsLauncher = registerPermissionsLaunch()

    /**
     * back button dispatcher
     */
    private val backDispatcher by lazy {
        onBackPressedDispatcher
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBackDispatcher {
            onBackDispatcher()
        }
        initBindingView()
        initView()
        initListener()
        initViewModel()
    }

    /**
     * init binding view
     */
    protected abstract fun initBindingView()

    /**
     * init view
     */
    protected open fun initView() {}

    /**
     * init listener
     */
    protected open fun initListener() {}

    /**
     * init viewModel
     */
    protected open fun initViewModel() {}

    /**
     * bind toolbar
     * @param iconId icon id
     */
    protected fun bindToolbar(toolbar: Toolbar?, iconId: Int) {
        activity.setSupportActionBar(toolbar)
        val bar = activity.supportActionBar
        if (bar != null) {
            bar.setDisplayShowTitleEnabled(false)
            bar.setDisplayHomeAsUpEnabled(true)
            bar.setHomeAsUpIndicator(iconId)
            bar.elevation = 0f
        }
    }

    private fun registerBackDispatcher(bloc: () -> Boolean) {
        backDispatcher.addCallback(this) {
            if (!bloc()) {
                finish()
            }
        }
    }

    /**
     * back button dispatcher
     * @return true not pass, false pass
     */
    open fun onBackDispatcher(): Boolean {
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            if (!onBackDispatcher()) {
                finish()
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * fragment switch show
     * @param tag tag
     */
    fun switchFragment(containerViewId: Int, targetFragment: Fragment?, tag: String? = null) {
        if (targetFragment == null) return
        val transaction = supportFragmentManager.beginTransaction()
        if (!targetFragment.isAdded && supportFragmentManager.findFragmentByTag(tag) == null) {
            currentFragment?.let {
                transaction.hide(it)
            }
            transaction.add(containerViewId, targetFragment, tag).commitNow()
        } else {
            currentFragment?.let {
                transaction.hide(it)
            }
            transaction.show(targetFragment).commitNow()
        }
        currentFragment = targetFragment
    }

    companion object {
        const val RESULT_DATA = "result_intent_data"
        const val DATA_KEY = "intent_data_key"
        const val DATA_KEY_TWO = "intent_data_key_two"
        const val DATA_KEY_THREE = "intent_data_key_three"
        const val DATA_KEY_FOUR = "intent_data_key_four"
    }

}