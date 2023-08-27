package com.yumenonaka.ymnkapp.screens.soundeffect

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.yumenonaka.ymnkapp.components.ButtonSoundEffect
import com.yumenonaka.ymnkapp.components.ShioriLoading
import com.yumenonaka.ymnkapp.models.app.LoadedSoundEffect
import com.yumenonaka.ymnkapp.models.app.SoundEffect

@Composable
fun SoundEffectButtons(
    soundEffectsList: List<Pair<Int, List<SoundEffect>>>,
) {
    val context = LocalContext.current
    val soundEffectState: SoundEffectState = rememberSoundEffectState(soundEffectsList)
    DisposableEffect(Unit) {
        onDispose { soundEffectState.releaseSoundPool() }
    }

     Column(Modifier.fillMaxHeight()) {
         TabRow(selectedTabIndex = soundEffectState.currentTab) {
             for (index in soundEffectsList.indices) {
                 Tab(
                     selected = true,
                     onClick = { soundEffectState.changeTab(index) }
                 ) {
                     Text(context.getString(soundEffectsList[index].first), Modifier.padding(vertical = 12.dp))
                 }
             }
         }
         if(soundEffectState.isLoading) {
             ShioriLoading()
         } else {
             SoundEffectList(loadedSoundEffects = soundEffectState.loadedSoundEffects, onClick = soundEffectState::playSound)
         }
     }
}

@Composable
private fun SoundEffectList(loadedSoundEffects:  List<LoadedSoundEffect>, onClick: (soundId: Int) -> Unit) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Spacer(modifier = Modifier.padding(4.dp))
        loadedSoundEffects.forEach {
            ButtonSoundEffect(text = it.name) {
                onClick(it.soundId)
            }
        }
    }
}
