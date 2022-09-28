package com.yumenonaka.ymnkapp.screens.soine

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.BitmapFactory
import android.os.IBinder
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.yumenonaka.ymnkapp.services.soine.SoinePlayerAction
import com.yumenonaka.ymnkapp.services.soine.SoinePlayerService

@Composable
fun rememberSoineState(context: Context, initPlaying: Boolean = false) = remember { SoineState(context, initPlaying) }

class SoineState(val context: Context, initPlaying: Boolean) {
    private lateinit var soineService: SoinePlayerService
    private var soineServiceBounded: Boolean = false
    var playing by mutableStateOf(initPlaying)
        private set

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(cn: ComponentName, service: IBinder) {
            val binder = service as SoinePlayerService.SoineBinder
            soineService = binder.getService()
            playing = soineService.isPlaying()
            soineServiceBounded = true
        }

        override fun onServiceDisconnected(cn: ComponentName) {
            soineServiceBounded = false
        }
    }

    fun bindSoinePlayerService() {
        context.bindService(getSoinePlayerServiceIntent(), connection, Context.BIND_AUTO_CREATE)
    }

    fun unbindSoinePlayerService() {
        context.unbindService(connection)
    }

    fun toggleSoine() {
        val intent = getSoinePlayerServiceIntent()
        intent.action = if(playing) {
            SoinePlayerAction.STOP.value
        } else {
            SoinePlayerAction.PLAY.value
        }
        context.startService(intent)
        playing = !playing
    }

    fun getSoineImage(): ImageBitmap {
        return if (playing) {
            BitmapFactory
                .decodeStream(context.assets.openFd("soine/shiori_eye_close.jpg").createInputStream())
                .asImageBitmap()
        } else {
            BitmapFactory
                .decodeStream(context.assets.openFd("soine/shiori_eye_open.jpg").createInputStream())
                .asImageBitmap()
        }
    }

    private fun getSoinePlayerServiceIntent(): Intent {
        return Intent(context, SoinePlayerService::class.java)
    }
}
