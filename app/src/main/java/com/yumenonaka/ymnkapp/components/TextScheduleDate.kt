package com.yumenonaka.ymnkapp.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextScheduleDate(scheduleDate: String) {
    Text(
        modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp),
        fontSize = 18.sp,
        text = scheduleDate
    )
}

@Preview(showBackground = true)
@Composable
fun TestTextScheduleDate() {
    TextScheduleDate("2021-10-16")
}
