package com.yumenonaka.ymnkapp.apis

import com.yumenonaka.ymnkapp.BuildConfig.YMNK_API_URL
import com.yumenonaka.ymnkapp.models.request.RecentSchedule
import com.yumenonaka.ymnkapp.models.request.RecentScheduleItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import okhttp3.Request

suspend fun getRecentSchedule(): ArrayList<RecentScheduleItem> {
    return withContext(Dispatchers.IO) {
        val request: Request = Request.Builder()
            .url("$YMNK_API_URL/recentEvent")
            .build()
        val result = HttpClient.execute(request)
        val recentSchedule = Json.decodeFromString<RecentSchedule>(result)
        recentSchedule.data
    }
}
