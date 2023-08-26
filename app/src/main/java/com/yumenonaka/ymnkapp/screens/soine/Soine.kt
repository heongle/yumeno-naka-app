package com.yumenonaka.ymnkapp.screens.soine

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.yumenonaka.ymnkapp.R
import com.yumenonaka.ymnkapp.components.FullScreenImage

@Preview(showBackground = true)
@Composable
fun Soine(soineState: SoineState = rememberSoineState(context = LocalContext.current), lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current) {
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when(event) {
                Lifecycle.Event.ON_START -> soineState.bindSoinePlayerService()
                Lifecycle.Event.ON_STOP -> soineState.unbindSoinePlayerService()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    SoineContent(soineState = soineState)
}

@Composable
fun SoineContent(soineState: SoineState) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) result@{
        if (!it) {
            return@result
        }
        soineState.toggleSoine()
    }

    val context = LocalContext.current
    FullScreenImage { soineState.getSoineImage() }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.88f)
            ),
            onClick = onClick@{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    return@onClick
                }
                soineState.toggleSoine()
            }
        ) {
            Text(
                fontSize = 24.sp,
                text = if(soineState.playing) context.getString(R.string.wake_up) else context.getString(R.string.sleep)
            )
        }
    }
}
