package com.yumenonaka.ymnkapp.utility

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool

fun playSoundEffect(context: Context, seFileName: String) {
    val afd: AssetFileDescriptor = context.assets.openFd("se/$seFileName")
    MediaPlayer().apply {
        setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build()
        )
        setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        setOnCompletionListener {
            it.reset()
            it.release()
        }
        prepare()
        start()
    }
}
