package com.yumenonaka.ymnkapp

import com.yumenonaka.ymnkapp.data.ShioriProfile
import com.yumenonaka.ymnkapp.utility.getShioriAging
import com.yumenonaka.ymnkapp.utility.getShioriBirthdayDiff
import org.junit.Test

import org.junit.Assert.*
import java.time.Instant
import java.time.ZoneId

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ShioriBirthdayUnitTest {
    @Test
    fun shiori_birthday_correctly_parsed() {
        val dt = getShioriBirthdayDiff(Instant.ofEpochSecond(ShioriProfile.firstBirthdayEpoch), Instant.ofEpochSecond(1658698224), ZoneId.of("Asia/Tokyo"))
        assertEquals(1, dt.days)
        assertEquals(17, dt.hours)
        assertEquals(29, dt.minutes)
        assertEquals(36, dt.seconds)
    }

    @Test
    fun shiori_age_correctly_calculated() {
        println(getShioriAging(ShioriProfile.firstBirthdayInstant, Instant.ofEpochSecond(1658698224)))
        assertEquals(36, 36)
    }
}
