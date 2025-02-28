package com.sheng.wang.common.permission

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.View
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar
import com.sheng.wang.common.R
import com.sheng.wang.common.base.BaseActivity
import com.sheng.wang.common.helper.getNavigationBarHeight
import java.lang.ref.WeakReference

/**
 * camera permission
 */
const val CAMERA = Manifest.permission.CAMERA

/**
 * record audio permission
 */
const val RECORD_AUDIO = Manifest.permission.RECORD_AUDIO

/**
 * camera record audio permission
 */
val CAMERA_AUDIO = arrayOf(CAMERA, RECORD_AUDIO)

/**
 * media permission（images/video）
 */
val READ_MEDIA_IMAGES_VIDEO = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    arrayOf(Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO)
} else {
    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
}

/**
 * images permission
 */
val READ_MEDIA_IMAGES = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    Manifest.permission.READ_MEDIA_IMAGES
} else {
    Manifest.permission.READ_EXTERNAL_STORAGE
}

/**
 * audio permission
 */
val READ_MEDIA_AUDIO = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    Manifest.permission.READ_MEDIA_AUDIO
} else {
    Manifest.permission.READ_EXTERNAL_STORAGE
}

/**
 * notification permission-13+
 */
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
val POST_NOTIFICATIONS = Manifest.permission.POST_NOTIFICATIONS

/**
 * permission onGranted
 */
var onPermissionGranted: WeakReference<(() -> Unit)>? = null

/**
 * permission onDenied
 *
 * * return true intercept oneself achieve,false use default achieve
 */
var onPermissionDenied: WeakReference<(() -> Boolean)>? = null


/**
 * request one permission-BaseActivity
 * @param permission permission
 * @param onGranted onGranted
 * @param onDenied onDenied
 */
fun Context?.requestPermission(
    permission: String?,
    onGranted: () -> Unit,
    onDenied: (() -> Boolean)? = null
) {
    this?.let {
        if (it is BaseActivity) {
            onPermissionGranted = WeakReference(onGranted)
            onPermissionDenied = WeakReference(onDenied)
            it.permissionLauncher.launch(permission)
        }
    }
}

/**
 * request more permission-BaseActivity
 * @param permissions permissions
 * @param onGranted onGranted
 * @param onDenied onDenied
 */
fun Context?.requestPermissions(
    permissions: Array<String>?,
    onGranted: () -> Unit,
    onDenied: (() -> Boolean)? = null
) {
    this?.let {
        if (it is BaseActivity) {
            onPermissionGranted = WeakReference(onGranted)
            onPermissionDenied = WeakReference(onDenied)
            it.permissionsLauncher.launch(permissions)
        }
    }
}

/**
 * request one permission
 * @param onGranted onGranted
 * @param onDenied onDenied
 */
fun ActivityResultCaller.registerPermissionLaunch(
    onGranted: () -> Unit,
    onDenied: (() -> Boolean?)? = null
): ActivityResultLauncher<String> {
    val context = if (this is FragmentActivity) this else (this as? Fragment)?.activity
    return registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
        if (result) {
            onGranted()
        } else {
            onDenied?.invoke().let {
                if (it == null || it == false) {
                    context.showDeniedView()
                }
            }
        }
    }
}

/**
 * request more permission
 * @param onGranted onGranted
 * @param onDenied onDenied
 */
fun ActivityResultCaller.registerPermissionsLaunch(
    onGranted: () -> Unit,
    onDenied: (() -> Boolean?)? = null
): ActivityResultLauncher<Array<String>> {
    val context = if (this is FragmentActivity) this else (this as? Fragment)?.activity
    return registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { resultMap ->
        if (resultMap.containsValue(false)) {
            onDenied?.invoke().let {
                if (it == null || it == false) {
                    context.showDeniedView()
                }
            }
        } else {
            onGranted()
        }
    }
}

/**
 * show onDenied view
 */
private fun FragmentActivity?.showDeniedView() {
    this?.let {
        val view = it.findViewById<View>(android.R.id.content)
        Snackbar.make(view, R.string.c_permission_msg, Snackbar.LENGTH_LONG)
            .setAction(R.string.c_permission_setting) {
                this.startSettingPermission()
            }.apply {
                this.view.setPadding(0, 0, 0, it.getNavigationBarHeight())
            }.show()
    }
}


/**
 * check window permission
 * @return true have ,false not have
 */
fun Context?.isWindowPermission(): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        return Settings.canDrawOverlays(this)
    }
    return true
}

/**
 * check is permission
 * @param permission permission
 * @return true have ，false not have
 */
fun Context?.isCheckPermission(permission: String): Boolean {
    this?.let {
        return ContextCompat.checkSelfPermission(it, permission) == PackageManager.PERMISSION_GRANTED
    }
    return false
}

/**
 * start app permission setting page
 */
fun Context?.startSettingPermission() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        .setData(Uri.fromParts("package", this?.packageName, null))
    this?.startActivity(intent)
}

/**
 * start app window permission setting page
 */
fun Context?.startSettingWindow() {
    val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            .setData(Uri.fromParts("package", this?.packageName, null))
    } else {
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            .setData(Uri.fromParts("package", this?.packageName, null))
    }
    this?.startActivity(intent)
}

/**
 * start app wifi setting page
 */
fun Context?.startSettingWifi() {
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
        this?.startActivity(Intent(Settings.Panel.ACTION_WIFI))
    } else {
        this?.startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
    }
}