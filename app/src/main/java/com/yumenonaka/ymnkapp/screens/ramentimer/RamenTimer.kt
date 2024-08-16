package com.yumenonaka.ymnkapp.screens.ramentimer

import android.view.WindowManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yumenonaka.ymnkapp.R
import com.yumenonaka.ymnkapp.libs.context.findActivity

@Composable
fun RamenTimer(
    state: RamenTimerState = rememberRamenTimerState()
) {
    val context = LocalContext.current

    DisposableEffect(Unit) {
        onDispose { state.releaseSoundPool() }
    }

    LaunchedEffect(state.timerStatus) {
        context.findActivity()?.let { activity ->
            when (state.timerStatus) {
                RamenTimerStatus.Running -> activity.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                RamenTimerStatus.Stopped, RamenTimerStatus.Completed -> activity.window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            }
        }
    }

    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            bitmap = state.getSarashiImage(),
            contentDescription = "Sarashi",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
        ) {
            Text(text = context.getString(R.string.sound_type), fontWeight = FontWeight.Bold)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                for (typeIndex in state.cktkTypeList.indices) {
                    OutlinedButton(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(0.dp),
                        onClick = { state.setCktkIndex(typeIndex) }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dp),
                        ) {
                            if (typeIndex == state.currentCktkTypeIndex) {
                                Icon(Icons.Rounded.Check, contentDescription = "Selected", Modifier.size(16.dp))
                            }
                            Text(text = "${typeIndex+1}")
                        }
                    }
                }
            }
        }
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
            Text(text = state.getRemainingTime(), fontSize = 50.sp)
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.88f)
            ),
            onClick = state::toggleTimer,
        ) {
            Text(
                text = when (state.timerStatus) {
                    RamenTimerStatus.Running -> context.getString(R.string.stop)
                    RamenTimerStatus.Stopped, RamenTimerStatus.Completed -> context.getString(R.string.start)
                },
                fontSize = 24.sp
            )
        }
    }
}
