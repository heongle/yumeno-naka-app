package com.yumenonaka.ymnkapp

import android.content.Context
import com.yumenonaka.ymnkapp.models.app.RouteData
import com.yumenonaka.ymnkapp.screens.birthday_counter.BirthdayCounter
import com.yumenonaka.ymnkapp.screens.links.Links
import com.yumenonaka.ymnkapp.screens.schedule.Schedule
import com.yumenonaka.ymnkapp.screens.sound_effect.SoundEffectButtons
import com.yumenonaka.ymnkapp.screens.sound_effect.kushamiEffects
import com.yumenonaka.ymnkapp.screens.sound_effect.nkoEffects
import com.yumenonaka.ymnkapp.screens.sound_effect.soundEffects

object RouteName {
    const val schedule = "schedule"
    const val birthdayCountdown = "birthday-countdown"
    const val soundEffect = "sound-effect"
    const val kushami = "sound-effect-kushami"
    const val nko = "sound-effect-nko"
    const val externalLinks = "external-links"
}

fun getRoutes(context: Context): List<RouteData> {
    return listOf(
        RouteData(context.getString(R.string.schedule), RouteName.schedule) { Schedule() },
        RouteData(context.getString(R.string.birthday_countdown), RouteName.birthdayCountdown, needDivide = true) { BirthdayCounter() },
        RouteData(context.getString(R.string.external_link), RouteName.externalLinks) { Links() },
        RouteData(context.getString(R.string.sound_effect), RouteName.soundEffect) { SoundEffectButtons(soundEffects) },
        RouteData(context.getString(R.string.kushami), RouteName.kushami) { SoundEffectButtons(kushamiEffects) },
        RouteData(context.getString(R.string.nko), RouteName.nko, needDivide = true) { SoundEffectButtons(nkoEffects) },
        RouteData(context.getString(R.string.external_link), RouteName.externalLinks) { Links() }
    )
}
