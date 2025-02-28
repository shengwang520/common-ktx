package com.sheng.wang.common.helper

import android.content.Context
import android.net.Uri
import android.os.Environment
import java.io.File
import java.io.FileOutputStream

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
fun Context.uriToPath(uri: Uri): String? {
    try {
        val imgFile = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        if (imgFile?.exists() == false) {
            imgFile.mkdir()
        }
        val file = File(imgFile?.absolutePath + File.separator + System.currentTimeMillis() + ".jpg")
        val fileInputStream = contentResolver?.openInputStream(uri)
        val fileOutputStream = FileOutputStream(file)
        val buffer = ByteArray(1024)
        var byteRead: Int
        while (-1 != fileInputStream?.read(buffer).also { byteRead = it ?: 0 }) {
            fileOutputStream.write(buffer, 0, byteRead)
        }
        fileInputStream?.close()
        fileOutputStream.flush()
        fileOutputStream.close()
        return file.absolutePath
    } catch (e: Exception) {
        e.printStackTrace()
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