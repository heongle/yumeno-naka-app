package com.yumenonaka.ymnkapp.screens.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yumenonaka.ymnkapp.apis.getRecentSchedule
import com.yumenonaka.ymnkapp.models.request.RecentScheduleItem
import com.yumenonaka.ymnkapp.utility.parseScheduleData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.IOException

class ScheduleViewModel : ViewModel() {
    private val _recentScheduleDataState = MutableStateFlow<LinkedHashMap<String, ArrayList<RecentScheduleItem>>?>(null)
    val recentScheduleDataState: StateFlow<LinkedHashMap<String, ArrayList<RecentScheduleItem>>?> = _recentScheduleDataState
    var dateKeySet: List<String>? = null

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try{
                val recentSchedule = getRecentSchedule()
                val parsedSchedule = parseScheduleData(recentSchedule)
                dateKeySet = ArrayList(parsedSchedule.keys)
                _recentScheduleDataState.value = parsedSchedule
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
        _recentScheduleDataState.value = null
        fetchData()
    }
}
