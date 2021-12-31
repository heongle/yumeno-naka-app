package com.yumenonaka.ymnkapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yumenonaka.ymnkapp.R

@Composable
fun ButtonSoundEffect(text:String, onClick: () -> Unit) {
    Box(modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)) {
        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onClick
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = text)
                Icon(
                    painterResource(R.drawable.outline_play_circle_24),
                    ""
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestButtonSoundEffect() {
    ButtonSoundEffect("Test") { }
}
