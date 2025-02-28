package com.sheng.wang.common.compat

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat

/**
 * activity page
 */
object AppCompat {

    fun startActivity(context: Context?, intent: Intent) {
        context?.startActivity(intent, null)
    }

    fun startActivities(context: Context?, vararg intent: Intent) {
        if (context != null) {
            ActivityCompat.startActivities(context, intent, null)
        }
    }

    fun startActivity(context: Context?, view: View, intent: Intent, transitionName: String) {
        if (context is Activity) {
            val option = ActivityOptionsCompat.makeSceneTransitionAnimation(context, view, transitionName)
            context.startActivity(intent, option.toBundle())
        } else {
            startActivity(context, intent)
        }
    }

    fun finishActivity(activity: Activity?) {
        if (activity != null) {
            ActivityCompat.finishAfterTransition(activity)
        }
    }

    fun <T> isActivityExist(context: Context?, activityClass: Class<T>): Boolean {
        val intent = Intent(context, activityClass)
        return context?.packageManager?.resolveActivity(intent, 0) != null
    }
}