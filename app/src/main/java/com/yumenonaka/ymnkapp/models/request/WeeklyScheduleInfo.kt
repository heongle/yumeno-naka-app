package com.yumenonaka.ymnkapp.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeeklyScheduleInfo(
    @SerialName("saved_path")
    val savedPath: String,
    @SerialName("tweet_created_at")
    val tweetCreatedAt: String,
    @SerialName("tweet_id")
    val tweetId: String
)
