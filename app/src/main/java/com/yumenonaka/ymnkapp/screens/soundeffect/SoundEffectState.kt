package com.yumenonaka.ymnkapp.screens.soundeffect

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.yumenonaka.ymnkapp.models.app.LoadedSoundEffect
import com.yumenonaka.ymnkapp.models.app.SoundEffect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun rememberSoundEffectState(
    soundEffectsList: List<Pair<Int, List<SoundEffect>>>,
    context: Context = LocalContext.current,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember {
    SoundEffectState(
        soundEffectsList = soundEffectsList,
        context = context,
        coroutineScope = coroutineScope
    )
}

class SoundEffectState(
    val soundEffectsList: List<Pair<Int, List<SoundEffect>>>,
    val context: Context,
    val coroutineScope: CoroutineScope
) {
    var currentTab by mutableIntStateOf(0)
        private set
    var isLoading by mutableStateOf(true)
    private var soundPool: SoundPool? = null
    private val _loadedSoundEffects = mutableListOf<LoadedSoundEffect>()
    val loadedSoundEffects: List<LoadedSoundEffect> = _loadedSoundEffects

    init {
        coroutineScope.launch {
            soundPool = SoundPool.Builder()
                .setMaxStreams(80)
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .build()
                )
                .build()
             loadSoundEffects(soundEffectsList[0].second)
        }
    }

    fun changeTab(tabIndex: Int) {
        if(currentTab == tabIndex) {
            return
        }
        currentTab = tabIndex
        coroutineScope.launch {
            loadSoundEffects(soundEffectsList[tabIndex].second)
        }
    }

    private suspend fun loadSoundEffects(soundEffects: List<SoundEffect>) {
        isLoading = true
        _loadedSoundEffects.clear()
        withContext(Dispatchers.IO) {
            soundPool?.let { sp ->
                soundEffects.forEach {
                    context.assets.openFd("se/${it.filePathFromSe}").use { afd ->
                        val soundId = sp.load(afd.fileDescriptor, afd.startOffset, afd.length, 1)
                        _loadedSoundEffects.add(LoadedSoundEffect(it.name, soundId))
                    }
                    delay(25)
                }
            }
        }
        isLoading = false
    }

    fun playSound(soundId: Int) {
        soundPool?.play(soundId, 1F,1F,1,0,1F)
    }

    fun releaseSoundPool() {
        soundPool?.release()
        soundPool = null
    }
}
