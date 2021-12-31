package com.yumenonaka.ymnkapp.models.request

import kotlinx.serialization.Serializable

@Serializable
data class RecentSchedule(
    val count: Int,
    val data: ArrayList<RecentScheduleItem>,
)

@Serializable
data class RecentScheduleItem(
    val eventDate: String,
    val eventName: String,
    val description: String? = null,
    val location: String? = null,
    val startTime: String? = null,
)
