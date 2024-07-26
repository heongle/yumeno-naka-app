package com.yumenonaka.ymnkapp.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yumenonaka.ymnkapp.R
import kotlinx.coroutines.delay

@Composable
fun ShioriLoading() {
    var i by remember { mutableIntStateOf(0) }
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

@Composable
fun ShioriLoadingIndicator(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(6.dp, Alignment.End),
    verticalAlignment: Alignment.Vertical = Alignment.Bottom,
    height: Dp = 35.dp,
    text: String = "Now Loading...",
    fontSize: TextUnit? = null,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "Loading Text Transition")
    val animatedAlpha by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Loading Text Animation"
    )
    val calculatedFontSize = fontSize ?: (height.value / 2).sp
    Row(
        modifier.height(height),
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment,
    ) {
        Text(modifier = Modifier.alpha(animatedAlpha), text = text, fontSize = calculatedFontSize)
        Image(
            painter = painterResource(id = +R.raw.loading_sm),
            modifier = Modifier.fillMaxHeight(),
            contentDescription = "Loading Indicator",
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TestShioriLoadingIndicator() {
    ShioriLoadingIndicator(Modifier.fillMaxWidth().padding(4.dp), height = 40.dp)
}
