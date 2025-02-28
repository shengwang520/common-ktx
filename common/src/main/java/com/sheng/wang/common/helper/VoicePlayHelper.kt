package com.sheng.wang.common.helper

import android.content.Context
import android.media.MediaPlayer

/**
 * voice playHelper
 */
object VoicePlayHelper {

    private var mPlayer: MediaPlayer? = null

    /**
     * play raw mp3
     */
    fun playRaw(mContext: Context, id: Int, isLoop: Boolean = false) {
        stopPlay()
        try {
            mPlayer = MediaPlayer()
            val file = mContext.resources.openRawResourceFd(id)
            mPlayer?.setDataSource(file.fileDescriptor, file.startOffset, file.length)
            if (!isPlaying) {
                mPlayer?.prepare()
                mPlayer?.start()
                mPlayer?.isLooping = isLoop //循环播放
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * stop play
     */
    fun stopPlay() {
        try {
            if (mPlayer != null && isPlaying) {
                mPlayer?.stop()
                mPlayer?.release()
            }
            mPlayer = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 是否正在播放
     */
    private val isPlaying: Boolean
        get() = mPlayer?.isPlaying == true

}