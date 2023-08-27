package com.yumenonaka.ymnkapp.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LabelSchedule(text: String, onClick: ()-> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 5.dp),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
        shape = RoundedCornerShape(15.dp),
        onClick = onClick
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp),
            text = text
        )
    }
}

@Composable
fun ButtonSchedule(onClick: ()-> Unit, content: @Composable RowScope.() -> Unit) {
    Button(
        modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 5.dp),
        shape = RoundedCornerShape(15.dp),
        onClick = onClick
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun TestLabelSchedule() {
    LabelSchedule("限界突破FC2配信\uD83D\uDC34") {}
}
