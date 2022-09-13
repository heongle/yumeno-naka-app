package com.yumenonaka.ymnkapp.screens.weeklyschedule

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yumenonaka.ymnkapp.R
import com.yumenonaka.ymnkapp.components.ButtonSchedule

@Composable
fun WeeklyScheduleImageView(imageByteArray: ByteArray, contentDescription: String, onSave: () -> Unit) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            bitmap = BitmapFactory.decodeByteArray(
                imageByteArray,
                0,
                imageByteArray.size
            ).asImageBitmap(),
            contentDescription = contentDescription,
            modifier = Modifier
                .fillMaxHeight(0.88f)
                .clip(RoundedCornerShape(5))
        )
        ButtonSchedule(onClick = onSave) {
            Text(
                modifier = Modifier.padding(vertical = 2.dp),
                fontSize = 16.sp,
                text = context.getString(R.string.save)
            )
        }
    }
}
