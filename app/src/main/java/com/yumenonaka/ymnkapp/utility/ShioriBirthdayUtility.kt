package com.yumenonaka.ymnkapp.utility

import com.yumenonaka.ymnkapp.models.app.DateTime
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

fun getShioriBirthdayDiff(birthdayInstant: Instant, currentInstant: Instant, zoneId: ZoneId): DateTime {
    val currentZonedDateTime = ZonedDateTime.ofInstant(currentInstant, zoneId)
    val birthdayZonedDateTime = ZonedDateTime
        .ofInstant(birthdayInstant, zoneId)
        .withYear(currentZonedDateTime.year)
        .withMonth(currentZonedDateTime.month.value)
    val nextBirthdayZonedDateTime = birthdayZonedDateTime
        .plusMonths(if(currentZonedDateTime.dayOfMonth < birthdayZonedDateTime.dayOfMonth) 0 else 1)
    val differenceInTime = nextBirthdayZonedDateTime.toEpochSecond() - currentZonedDateTime.toEpochSecond()

    return DateTime (
        days = ((differenceInTime / (60 * 60 * 24)) % 365).toInt(),
        hours = ((differenceInTime / (60 * 60)) % 24).toInt(),
        minutes = ((differenceInTime / 60) % 60).toInt(),
        seconds = (differenceInTime % 60).toInt()
    )
}

fun getShioriAging(firstBirthdayInstant: Instant, currentInstant: Instant): Long {
    val d1 = LocalDateTime.ofInstant(firstBirthdayInstant, ZoneId.systemDefault())
    val d2 = LocalDateTime.ofInstant(currentInstant, ZoneId.systemDefault())
    return ChronoUnit.MONTHS.between(d1, d2)
}
