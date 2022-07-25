package com.yumenonaka.ymnkapp.screens.soundeffect

import android.media.SoundPool
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yumenonaka.ymnkapp.components.ButtonSoundEffect
import com.yumenonaka.ymnkapp.components.ShioriLoading
import com.yumenonaka.ymnkapp.models.app.LoadedSoundEffect
import com.yumenonaka.ymnkapp.models.app.SoundEffect

@Composable
fun SoundEffectButtons(soundEffects: List<SoundEffect>, soundEffectViewModel: SoundEffectViewModel = viewModel()) {
    val context = LocalContext.current
    val soundPoolState = soundEffectViewModel.soundPool.collectAsState()
    DisposableEffect(context) {
        soundEffectViewModel.initialize(context, soundEffects)
        onDispose { soundEffectViewModel.releaseSoundPool() }
    }

    if(soundPoolState.value == null) {
        ShioriLoading()
    } else {
        Content(loadedSoundEffects = soundEffectViewModel.loadedSoundEffects, soundPool = soundPoolState.value!!)
    }
}

@Composable
private fun Content(loadedSoundEffects:  ArrayList<LoadedSoundEffect>, soundPool: SoundPool) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Spacer(modifier = Modifier.padding(4.dp))
        loadedSoundEffects.forEach {
            ButtonSoundEffect(text = it.name) {
                soundPool.play(it.soundId, 1F,1F,1,0,1F)
            }
        }
    }
}
