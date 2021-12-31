package com.yumenonaka.ymnkapp.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FrameScheduleDescription(description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 5.dp),
        shape = RoundedCornerShape(15.dp),
        backgroundColor = Color(0xfff2ebf5),
        border = BorderStroke(2.dp, Color(0xff606060))
    ) {
        Text(
            modifier = Modifier.padding(
                top = 9.5.dp,
                end = 17.dp,
                bottom = 9.5.dp,
                start = 17.dp
            ),
            text = description
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TestFrameScheduleDescription() {
    FrameScheduleDescription(
        """
            Sunflower seed ASMR. Let's chat together
            
            向日葵种子ASMR。休闲杂谈
        """.trimIndent()
    )
}
