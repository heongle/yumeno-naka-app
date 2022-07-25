package com.yumenonaka.ymnkapp.screens.birthday_counter

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Instant

private val countdownImageList = listOf("img1", "img2", "img3", "img4", "img5", "img6", "img7")

@Composable
fun rememberBirthdayCounterState(coroutineScope: CoroutineScope = rememberCoroutineScope(), context: Context = LocalContext.current) = remember {
    BirthdayCounterState(coroutineScope, context)
}

class BirthdayCounterState(coroutineScope: CoroutineScope, context: Context) {
    val img = BitmapFactory.decodeStream(context.assets.openFd("birthday-countdown-bg/${countdownImageList.random()}.jpg").createInputStream()).asImageBitmap()
    var currentInstant: Instant by mutableStateOf(Instant.now())
        private set
    init {
        coroutineScope.launch {
            while (true) {
                currentInstant = Instant.now()
                delay(500)
            }
        }
    }
}
