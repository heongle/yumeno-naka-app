package com.yumenonaka.ymnkapp.screens.schedule

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
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

class ScheduleState(val context: Context, val coroutineScope: CoroutineScope) {
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
            } catch (e: ResponseException) {
                Toast.makeText(context, e.message ?: "Error", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun refresh() {
        recentScheduleDataState = null
        fetchData()
    }

    fun onResume() {
        fetchData()
    }
}
