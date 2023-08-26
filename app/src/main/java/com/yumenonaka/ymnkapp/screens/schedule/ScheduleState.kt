package com.yumenonaka.ymnkapp.screens.schedule

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.yumenonaka.ymnkapp.apis.HttpResult
import com.yumenonaka.ymnkapp.apis.getRecentSchedule
import com.yumenonaka.ymnkapp.models.request.RecentScheduleItem
import com.yumenonaka.ymnkapp.utility.parseScheduleData
import io.ktor.client.plugins.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException

@Composable
fun rememberScheduleState(context: Context = LocalContext.current, coroutineScope: CoroutineScope = rememberCoroutineScope()) = remember {
    ScheduleState(context = context, coroutineScope = coroutineScope)
}

//typealias ParsedRecentSchedule = LinkedHashMap<String, List<RecentScheduleItem>>
data class ScheduleEventGroup(
    val date: String,
    val events: List<RecentScheduleItem>
)

class ScheduleState(val context: Context, val coroutineScope: CoroutineScope) {
    var recentSchedule by mutableStateOf<HttpResult<List<ScheduleEventGroup>>>(HttpResult.Loading)
        private set

    private fun fetchData() {
        coroutineScope.launch {
            try {
                val res = getRecentSchedule()
                val parsedSchedule = parseScheduleData(res)
                val scheduleEventGroups = parsedSchedule.keys.toList().map {
                    ScheduleEventGroup(
                        date = it,
                        events = parsedSchedule[it] ?: listOf()
                    )
                }
                recentSchedule = HttpResult.Success(scheduleEventGroups)
            } catch (e: IOException) {
                e.printStackTrace()
                delay(2500)
                fetchData()
            } catch (e: ResponseException) {
                Toast.makeText(context, e.message ?: "Error", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun refresh() {
        recentSchedule = HttpResult.Loading
        fetchData()
    }

    fun onResume() {
        fetchData()
    }
}
