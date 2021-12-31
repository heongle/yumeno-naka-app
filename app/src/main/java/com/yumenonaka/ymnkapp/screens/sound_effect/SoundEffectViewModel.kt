package com.yumenonaka.ymnkapp.screens.sound_effect

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.AudioAttributes
import android.media.SoundPool
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yumenonaka.ymnkapp.models.app.LoadedSoundEffect
import com.yumenonaka.ymnkapp.models.app.SoundEffect
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SoundEffectViewModel: ViewModel() {
    private val _soundPool = MutableStateFlow<SoundPool?>(null)
    val soundPool: StateFlow<SoundPool?> = _soundPool
    val loadedSoundEffects: ArrayList<LoadedSoundEffect> = ArrayList()

    fun initialize(context: Context, soundEffects: List<SoundEffect>) {
        viewModelScope.launch {
            _soundPool.value = loadSoundEffects(context, soundEffects)
        }
    }

    private suspend fun loadSoundEffects(context: Context, soundEffects: List<SoundEffect>): SoundPool {
        return withContext(Dispatchers.IO) {
            val soundPool = SoundPool.Builder()
                .setMaxStreams(100)
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .build()
                )
                .build()
            soundEffects.forEach {
                val afd: AssetFileDescriptor = context.assets.openFd("se/${it.filePathFromSe}")
                val soundId = soundPool.load(afd.fileDescriptor, afd.startOffset, afd.length, 1)
                loadedSoundEffects.add(LoadedSoundEffect(it.name, soundId))
                delay(25)
            }
            soundPool
        }
    }

    fun releaseSoundPool() {
        _soundPool.value?.release()
        _soundPool.value = null
    }
}
