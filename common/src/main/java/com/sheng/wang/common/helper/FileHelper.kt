package com.sheng.wang.common.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.math.min

/**
 * create file
 * @param type see [Environment]
 * @param suffix file suffix
 */
fun Context?.createFile(type: String?, suffix: String?): File? {
    try {
        val storageDir = this?.getExternalFilesDir(type)
        return File.createTempFile(System.currentTimeMillis().toString(), suffix, storageDir)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

/**
 * delete file
 */
fun String?.deleteFile() {
    this?.let {
        val file = File(it)
        if (file.exists()) {
            file.delete()
        }
    }
}

/**
 * uri to file path
 */
fun Context.uriToPath(uri: Uri, isCompress: Boolean = true, minSize: Int = 1080): String? {
    try {
        val fileInputStream = contentResolver?.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(fileInputStream)
        return if (isCompress) {
            bitmap.compress(minSize).savePath(this)
        } else {
            bitmap.savePath(this)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

/**
 * path to bitmap
 */
fun String?.toBitmap(): Bitmap? {
    this?.let {
        var bitmap: Bitmap
        try {
            bitmap = BitmapFactory.decodeFile(it)
        } catch (e: OutOfMemoryError) {
            e.printStackTrace()
            System.gc()
            val opts = BitmapFactory.Options()
            opts.inSampleSize = 4
            bitmap = BitmapFactory.decodeFile(it, opts)
        }
        return bitmap
    }
    return null
}

/**
 * bitmap compress scale
 * @param minSize compress mis size
 */
fun Bitmap.compress(minSize: Int = 1080): Bitmap {
    val width = this.width
    val height = this.height
    if (min(width, height) > minSize) {
        val newWidth: Int
        val newHeight: Int
        if (width > height) {
            newHeight = minSize
            newWidth = (width / height.toFloat() * newHeight).toInt()
        } else if (width < height) {
            newWidth = minSize
            newHeight = (height / width.toFloat() * newWidth).toInt()
        } else {
            newHeight = minSize
            newWidth = newHeight
        }
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        val results = Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
        this.recycle()
        return results
    }
    return this
}

/**
 * bitmap save jpg file
 */
fun Bitmap.savePath(context: Context?): String? {
    context?.let {
        val file = it.createFile(Environment.DIRECTORY_PICTURES, ".jpg")
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(file)
            this.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            return file?.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            fos?.close()
        }
    }
    return null
}

/**
 * path get new file name
 */
fun String?.getFileName(): String {
    this?.let {
        try {
            return System.currentTimeMillis().toString() + it.substring(it.lastIndexOf("."))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return System.currentTimeMillis().toString()
}

/**
 * compress image
 * @param path local file path
 * @param minSize compress minSize default 1080P
 */
suspend fun Context?.compress(path: String, minSize: Int = 1080): String {
    return withContext(Dispatchers.IO) {
        path.toBitmap()?.compress(minSize)?.savePath(this@compress) ?: path
    }
}