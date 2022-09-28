package com.yumenonaka.ymnkapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale

@Composable
fun FullScreenImage(contentDescription: String? = null, imageProvider: () -> ImageBitmap) {
    Image(
        bitmap = imageProvider(),
        contentDescription = contentDescription,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillHeight
    )
}
