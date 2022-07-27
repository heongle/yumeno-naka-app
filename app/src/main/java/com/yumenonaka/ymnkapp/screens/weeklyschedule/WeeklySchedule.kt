package com.yumenonaka.ymnkapp.screens.weeklyschedule

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.yumenonaka.ymnkapp.R
import com.yumenonaka.ymnkapp.components.ShioriLoading

@Preview(showBackground = true)
@Composable
fun WeeklySchedule(weeklyScheduleState: WeeklyScheduleState = rememberWeeklyScheduleState()) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
        if (it) {
            weeklyScheduleState.saveImageToPictures()
        } else {
            Toast.makeText(weeklyScheduleState.context, weeklyScheduleState.context.getString(R.string.need_permission_to_save_image), Toast.LENGTH_LONG).show()
        }
    }

    if (weeklyScheduleState.weeklyScheduleImageBytes == null) {
        ShioriLoading()
    } else {
        WeeklyScheduleImageView(
            imageByteArray = weeklyScheduleState.weeklyScheduleImageBytes!!,
            contentDescription = weeklyScheduleState.weeklyScheduleImageName!!,
            onSave = {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                    launcher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                } else {
                    weeklyScheduleState.saveImageToPictures()
                }
            }
        )
    }
}


