package com.yumenonaka.ymnkapp.data

import java.time.Instant

object ShioriProfile {
    const val firstBirthdayEpoch: Long = 1527346800
    val firstBirthdayInstant: Instant = Instant.ofEpochSecond(firstBirthdayEpoch)
    const val initialAge: Int = 100000000
}
