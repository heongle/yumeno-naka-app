package com.yumenonaka.ymnkapp.screens.schedule

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.yumenonaka.ymnkapp.apis.getRecentSchedule
import com.yumenonaka.ymnkapp.libs.requester.HttpRequester
import com.yumenonaka.ymnkapp.models.request.RecentScheduleItem
import com.yumenonaka.ymnkapp.utility.parseScheduleData
import io.ktor.client.plugins.ResponseException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun rememberScheduleState(
    context: Context = LocalContext.current,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) = remember {
    ScheduleState(context = context, coroutineScope = coroutineScope)
}

data class ScheduleEventGroup(
    val date: String,
    val events: List<RecentScheduleItem>
)

class ScheduleState(val context: Context, val coroutineScope: CoroutineScope) {
    private var scheduleDataRequester = HttpRequester(fetcher = {
        val res = getRecentSchedule()
        val parsedSchedule = parseScheduleData(res)
        parsedSchedule.keys.toList().map {
            ScheduleEventGroup(
                date = it,
                events = parsedSchedule[it] ?: listOf()
            )
        }
    })
    val recentSchedule = { scheduleDataRequester.data }

    private fun fetchData() {
        coroutineScope.launch {
            try {
                scheduleDataRequester.startFetch(errorMessage = { e -> e.message })
            } catch (e: ResponseException) {
                Toast.makeText(context, e.message ?: "Error", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun refresh() {
        fetchData()
    }

    fun onResume() {
        fetchData()
    }
}
