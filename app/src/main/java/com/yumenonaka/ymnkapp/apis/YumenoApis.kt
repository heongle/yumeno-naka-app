package com.yumenonaka.ymnkapp.apis

import com.yumenonaka.ymnkapp.BuildConfig.YMNK_API_URL
import com.yumenonaka.ymnkapp.DI.httpClient
import com.yumenonaka.ymnkapp.models.request.RecentSchedule
import com.yumenonaka.ymnkapp.models.request.RecentScheduleItem
import com.yumenonaka.ymnkapp.models.request.WeeklyScheduleInfo
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun getRecentSchedule(): ArrayList<RecentScheduleItem> {
    return withContext(Dispatchers.IO) {
        httpClient.get("$YMNK_API_URL/recentEvent").body<RecentSchedule>().data
    }
}

suspend fun getLatestWeeklyScheduleInfo(): WeeklyScheduleInfo {
    return withContext(Dispatchers.IO) {
        httpClient.get("$YMNK_API_URL/weekly-schedule/latest-info").body()
    }
}
