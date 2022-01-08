package com.yumenonaka.ymnkapp.apis

import com.yumenonaka.ymnkapp.BuildConfig.YMNK_API_URL
import com.yumenonaka.ymnkapp.DI.httpClient
import com.yumenonaka.ymnkapp.models.request.RecentSchedule
import com.yumenonaka.ymnkapp.models.request.RecentScheduleItem
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun getRecentSchedule(): ArrayList<RecentScheduleItem> {
    return withContext(Dispatchers.IO) {
        httpClient.get<RecentSchedule>("$YMNK_API_URL/recentEvent").data
    }
}
