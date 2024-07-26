package com.yumenonaka.ymnkapp.screens.schedule

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.yumenonaka.ymnkapp.apis.HttpResult
import com.yumenonaka.ymnkapp.components.FrameScheduleDescription
import com.yumenonaka.ymnkapp.components.LabelSchedule
import com.yumenonaka.ymnkapp.components.ShioriLoading
import com.yumenonaka.ymnkapp.components.ShioriLoadingIndicator
import com.yumenonaka.ymnkapp.components.TextScheduleDate
import com.yumenonaka.ymnkapp.models.request.RecentSchedule
import com.yumenonaka.ymnkapp.models.request.RecentScheduleItem
import com.yumenonaka.ymnkapp.utility.parseScheduleData
import com.yumenonaka.ymnkapp.utility.parseScheduleDescription
import kotlinx.serialization.json.Json

@Composable
fun Schedule(scheduleViewState: ScheduleState = rememberScheduleState(), lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current) {
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                scheduleViewState.onResume()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    scheduleViewState.recentSchedule().let {
        when(it) {
            is HttpResult.Loading -> ShioriLoading()
            is HttpResult.Error -> {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Text(it.message, color = Color.Red, fontWeight = FontWeight.Bold)
                }
            }
            is HttpResult.Success -> {
                ScheduleList(
                    onSwipe = scheduleViewState::refresh,
                    recentSchedule = it,
                )
            }
        }
    }
}

@Composable
private fun ScheduleList(onSwipe: () -> Unit, recentSchedule: HttpResult.Success<List<ScheduleEventGroup>>) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(false),
        onRefresh = onSwipe,
        swipeEnabled = !recentSchedule.isValidating,
    ) {
        Column {
            AnimatedVisibility(
                visible = recentSchedule.isValidating,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                ShioriLoadingIndicator(
                    Modifier.fillMaxWidth().padding(5.dp)
                )
            }
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                for (scheduleGroup in recentSchedule.data) {
                    TextScheduleDate(scheduleGroup.date)
                    for (event in scheduleGroup.events) {
                        ScheduleItem(event = event)
                    }
                }
                Box(modifier = Modifier.height(150.dp))
            }
        }
    }
}

@Composable
private fun ScheduleItem(event: RecentScheduleItem) {
    var isDescShowing by remember { mutableStateOf(false) }
    val scheduleTime: String = if(event.startTime != null) event.startTime + "  " else ""
    LabelSchedule(text = scheduleTime + event.eventName) { isDescShowing = !isDescShowing }
    AnimatedVisibility(
        visible = isDescShowing,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut()
    ) {
        FrameScheduleDescription(parseScheduleDescription(event.description ?: "N/A"))
    }
}

@Preview(showBackground = true)
@Composable
fun TestScheduleList() {
    val data = Json.decodeFromString<RecentSchedule>("""
        {
        "data":[
        {"eventName":"\u9650\u754c\u7a81\u7834FC2\u914d\u4fe1\ud83d\udc34","eventDate":"2021-12-17","startTime":"23:00"},
        {"eventName":"\u3061\u3087\u3063\u3074\u308a\u3048\u3061\u3061\u306a\u30cb\u30b3\u30cb\u30b3\u9650\u5b9a\u914d\u4fe1\u2665","eventDate":"2021-12-22","startTime":"23:00","location":"https:\/\/ch.nicovideo.jp\/yumenoshiori","description":"Slightly ecchi live stream at niconico<br \/>\n<br \/>\n\u7a0d\u5faeH\u7684N\u7ad9\u76f4\u64ad"},
        {"eventName":"\u8a95\u751f\u65e5","eventDate":"2021-12-27","description":"Birthday<br \/>\n<br \/>\n\u751f\u65e5"},
        {"eventName":"\u8a95\u751f\u65e5 3D\u914d\u4fe1\u3057\u3061\u3083\u3046\u3088 #\u30e6\u30e1\u30ce\u30b7\u30aa\u30ea3D","eventDate":"2021-12-27","startTime":"22:00","location":"https:\/\/live.bilibili.com\/14052636","description":"#\u30e6\u30e1\u30ce\u30b7\u30aa\u30ea3D<br \/>\n<br \/>\nBirthday : 3D avatar live streaming<br \/>\n<br \/>\n\u751f\u65e5\uff1a3D\u76f4\u64ad"},
        {"eventName":"\u30ea\u30f3\u30af BILIBILI","eventDate":"2022-01-01","location":"https:\/\/live.bilibili.com\/14052636","description":"\u30e9\u30a4\u30d6\uff01<a href=\"https:\/\/live.bilibili.com\/14052636\">https:\/\/live.bilibili.com\/14052636<\/a><br>\u30c1\u30e3\u30f3\u30cd\u30eb\u30c8\u30c3\u30d7\uff01<br><a href=\"https:\/\/space.bilibili.com\/372984197\">https:\/\/space.bilibili.com\/372984197<\/a>&nbsp;&nbsp;"},
        {"eventName":"\u3061\u3087\u3063\u3074\u308a\u3048\u3061\u3061\u306a\u30cb\u30b3\u30cb\u30b3\u9650\u5b9a\u914d\u4fe1\u2665","eventDate":"2022-01-12","startTime":"23:00","location":"https:\/\/ch.nicovideo.jp\/yumenoshiori","description":"Slightly ecchi live stream at niconico<br \/>\n<br \/>\n\u7a0d\u5faeH\u7684N\u7ad9\u76f4\u64ad"},
        {"eventName":"\u9650\u754c\u7a81\u7834FC2\u914d\u4fe1\ud83d\udc34","eventDate":"2022-01-21","startTime":"23:00"},
        {"eventName":"\u3061\u3087\u3063\u3074\u308a\u3048\u3061\u3061\u306a\u30cb\u30b3\u30cb\u30b3\u9650\u5b9a\u914d\u4fe1\u2665","eventDate":"2022-01-26","startTime":"23:00","location":"https:\/\/ch.nicovideo.jp\/yumenoshiori","description":"Slightly ecchi live stream at niconico<br \/>\n<br \/>\n\u7a0d\u5faeH\u7684N\u7ad9\u76f4\u64ad"},
        {"eventName":"\u8a95\u751f\u65e5","eventDate":"2022-01-27","description":"Birthday<br \/>\n<br \/>\n\u751f\u65e5"},
        {"eventName":"\u8a95\u751f\u65e5 3D\u914d\u4fe1\u3057\u3061\u3083\u3046\u3088 #\u30e6\u30e1\u30ce\u30b7\u30aa\u30ea3D","eventDate":"2022-01-27","startTime":"22:00","location":"https:\/\/live.bilibili.com\/14052636","description":"#\u30e6\u30e1\u30ce\u30b7\u30aa\u30ea3D<br \/>\n<br \/>\nBirthday : 3D avatar live streaming<br \/>\n<br \/>\n\u751f\u65e5\uff1a3D\u76f4\u64ad"}
        ],"count":10}
    """.trimIndent())
    val parsedSchedule = parseScheduleData(data.data)
    val scheduleEventGroups = parsedSchedule.keys.toList().map {
        ScheduleEventGroup(
            date = it,
            events = parsedSchedule[it] ?: listOf()
        )
    }
    ScheduleList(onSwipe = {} , recentSchedule = HttpResult.Success(scheduleEventGroups, true))
}
