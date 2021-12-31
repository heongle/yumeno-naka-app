package com.yumenonaka.ymnkapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.yumenonaka.ymnkapp.R
import kotlinx.coroutines.delay

@Composable
fun ShioriLoading() {
    var i by remember { mutableStateOf(0) }
    val images = arrayOf(
        painterResource(id = +R.raw.load_1),
        painterResource(id = +R.raw.load_2),
        painterResource(id = +R.raw.load_3),
        painterResource(id = +R.raw.load_4)
    )
    LaunchedEffect(i) {
        delay(280)
        if (++i >= images.size) i = 0
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = images[i],
            modifier = Modifier.fillMaxSize(),
            contentDescription = "Loading Image"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TestShioriLoading() {
    ShioriLoading()
}
