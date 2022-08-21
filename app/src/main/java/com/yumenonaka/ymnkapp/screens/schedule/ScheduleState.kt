package com.yumenonaka.ymnkapp.screens.schedule

import androidx.compose.runtime.*
import com.yumenonaka.ymnkapp.apis.getRecentSchedule
import com.yumenonaka.ymnkapp.models.request.RecentScheduleItem
import com.yumenonaka.ymnkapp.utility.parseScheduleData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException

@Composable
fun rememberScheduleState(coroutineScope: CoroutineScope = rememberCoroutineScope()) = remember {
    ScheduleState(coroutineScope = coroutineScope)
}

class ScheduleState(val coroutineScope: CoroutineScope) {
    var recentScheduleDataState by mutableStateOf<LinkedHashMap<String, List<RecentScheduleItem>>?>(null)
        private set
    var dateKeySet: List<String>? = null

    private fun fetchData() {
        coroutineScope.launch {
            try {
                val recentSchedule = getRecentSchedule()
                val parsedSchedule = parseScheduleData(recentSchedule)
                dateKeySet = parsedSchedule.keys.toList()
                recentScheduleDataState = parsedSchedule
            } catch (e: IOException) {
                e.printStackTrace()
                delay(2500)
                fetchData()
            }
        }
    }

    fun refresh() {
        onStart()
    }

    fun onStart() {
        recentScheduleDataState = null
        fetchData()
    }
}
