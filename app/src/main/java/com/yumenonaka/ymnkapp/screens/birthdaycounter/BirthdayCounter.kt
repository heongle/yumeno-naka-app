package com.yumenonaka.ymnkapp.screens.birthdaycounter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yumenonaka.ymnkapp.R
import com.yumenonaka.ymnkapp.data.ShioriProfile
import com.yumenonaka.ymnkapp.utility.getShioriAging
import com.yumenonaka.ymnkapp.utility.getShioriBirthdayDiff
import java.time.Instant
import java.time.ZoneId

@Composable
fun BirthdayCounter(birthdayCounterViewState: BirthdayCounterState = rememberBirthdayCounterState()) {
    Box {
        Image(
            bitmap = birthdayCounterViewState.img,
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillHeight
        )
        Row(
            modifier = Modifier.fillMaxSize().background(Color(0,0,0,90)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RemainingTimeCounter { birthdayCounterViewState.currentInstant }
                Spacer(modifier = Modifier.size(28.dp))
                AgeCounter { birthdayCounterViewState.currentInstant }
            }
        }
    }
}

@Composable
private fun CounterText(text: String) {
    Text(
        color = Color.White,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        text = text
    )
}

@Composable
private fun RemainingTimeCounter(currentInstantProvider: () -> Instant) {
    val context = LocalContext.current
    val untilNextBirthdayText by remember {
        derivedStateOf {
            val dt = getShioriBirthdayDiff(ShioriProfile.firstBirthdayInstant, currentInstantProvider(), ZoneId.of("Asia/Tokyo"))
            "${dt.days}${context.getString(R.string.days)} " +
            "${dt.hours}${context.getString(R.string.hours)} " +
            "${dt.minutes}${context.getString(R.string.minutes)} " +
            "${dt.seconds}${context.getString(R.string.seconds)}"
        }
    }
    CounterText(context.getString(R.string.until_next_birthday))
    CounterText(untilNextBirthdayText)
}

@Composable
private fun AgeCounter(currentInstantProvider: () -> Instant) {
    val context = LocalContext.current
    val shioriTotalAge by remember {
        derivedStateOf { ShioriProfile.initialAge + getShioriAging(ShioriProfile.firstBirthdayInstant, currentInstantProvider()) }
    }
    CounterText(context.getString(R.string.million_age_counter))
    CounterText("$shioriTotalAge")
}

@Preview(showBackground = true)
@Composable
fun TestBirthdayCounter() {
    BirthdayCounter()
}
