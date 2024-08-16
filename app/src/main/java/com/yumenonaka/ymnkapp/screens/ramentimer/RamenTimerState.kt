package com.yumenonaka.ymnkapp.screens.ramentimer

import android.content.Context
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.floor

@Composable
fun rememberRamenTimerState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    context: Context = LocalContext.current,
) = remember {
    RamenTimerState(coroutineScope, context)
}

enum class RamenTimerStatus {
    Running,
    Stopped,
    Completed
}

class RamenTimerState(
    private val coroutineScope: CoroutineScope,
    private val context: Context,
) {
    private val sarashi = listOf(
        BitmapFactory.decodeStream(context.assets.openFd("ramen-timer/img/no-sarashi.jpg").createInputStream()).asImageBitmap(),
        BitmapFactory.decodeStream(context.assets.openFd("ramen-timer/img/sarashi.jpg").createInputStream()).asImageBitmap(),
    )
    private val soundPool: SoundPool = SoundPool.Builder()
        .setMaxStreams(10)
        .setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build()
        )
        .build()

    val cktkTypeList = listOf(1,2,3,4)
    private val _cks: List<Int> = cktkTypeList.map {
        context.assets.openFd("ramen-timer/audio/ck$it.mp3").use { afd ->
            soundPool.load(afd.fileDescriptor, afd.startOffset, afd.length, 1)
        }
    }
    private val _tks: List<Int> = cktkTypeList.map {
        context.assets.openFd("ramen-timer/audio/tk$it.mp3").use { afd ->
            soundPool.load(afd.fileDescriptor, afd.startOffset, afd.length, 1)
        }
    }

    var timerStatus by mutableStateOf(RamenTimerStatus.Stopped)
        private set
    private val initCounterValue = 180
    private var counter by mutableIntStateOf(initCounterValue)
    private var cktkJob: Job? = null
    var currentCktkTypeIndex by mutableIntStateOf(0)

    fun getSarashiImage(): ImageBitmap {
        return when (timerStatus) {
            RamenTimerStatus.Completed -> sarashi[0]
            else -> sarashi[1]
        }
    }

    fun setCktkIndex(typeId: Int) {
        currentCktkTypeIndex = typeId
    }

    fun getRemainingTime(): String {
        val minutes = floor(counter / 60.00).toInt()
        val seconds = counter % 60
        return "${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
    }

    private fun stopTimer() {
        cktkJob?.cancel("Timer Manually Stopped")
        timerStatus = RamenTimerStatus.Stopped
    }

    private fun startTimer() {
        timerStatus = RamenTimerStatus.Running
        cktkJob = coroutineScope.launch {
            while (timerStatus == RamenTimerStatus.Running) {
                playSound(_cks[currentCktkTypeIndex])
                delay(480)
                playSound(_tks[currentCktkTypeIndex])
                delay(480)
                counter -= 1
                if(counter == 0) {
                    playRamenDekita()
                    timerStatus = RamenTimerStatus.Completed
                }
            }
        }
    }

    fun toggleTimer() {
        when (timerStatus) {
            RamenTimerStatus.Running -> stopTimer()
            RamenTimerStatus.Stopped, RamenTimerStatus.Completed -> {
                counter = initCounterValue
                startTimer()
            }
        }
    }

    private fun playSound(soundId: Int) {
        soundPool.play(soundId, 1F,1F,1,0,1F)
    }

    fun releaseSoundPool() {
        soundPool.release()
    }

    private fun playRamenDekita() {
        MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(context.assets.openFd("ramen-timer/audio/ramendekita.mp3"))
            setOnPreparedListener {
                start()
            }
            setOnCompletionListener {
                stop()
                release()
            }
            prepare()
        }
    }
}
