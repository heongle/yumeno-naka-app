package com.yumenonaka.ymnkapp

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.yumenonaka.ymnkapp.screens.birthdaycounter.BirthdayCounter
import com.yumenonaka.ymnkapp.screens.links.Links
import com.yumenonaka.ymnkapp.screens.schedule.Schedule
import com.yumenonaka.ymnkapp.screens.soundeffect.SoundEffectButtons
import com.yumenonaka.ymnkapp.screens.soundeffect.kushamiEffects
import com.yumenonaka.ymnkapp.screens.soundeffect.nkoEffects
import com.yumenonaka.ymnkapp.screens.soundeffect.soundEffects
import com.yumenonaka.ymnkapp.screens.weeklyschedule.WeeklySchedule

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    val content: @Composable (NavBackStackEntry) -> Unit,
    val needDivide: Boolean = false
) {
    object Schedule : Screen("schedule", R.string.schedule, { Schedule() })
    object WeeklySchedule : Screen("weekly-schedule", R.string.weekly_schedule, { WeeklySchedule() })
    object BirthdayCountdown : Screen("birthday-countdown", R.string.birthday_countdown, { BirthdayCounter() }, true)
    object SoundEffect : Screen("sound-effect", R.string.sound_effect, { SoundEffectButtons(soundEffects) })
    object Kushami : Screen("sound-effect-kushami", R.string.kushami, { SoundEffectButtons(kushamiEffects) })
    object NKO : Screen("sound-effect-nko", R.string.nko, { SoundEffectButtons(nkoEffects) }, true)
    object ExternalLink : Screen("external-links", R.string.external_link, { Links() })
}

val routes = listOf(
    Screen.Schedule,
    Screen.WeeklySchedule,
    Screen.BirthdayCountdown,
    Screen.SoundEffect,
    Screen.Kushami,
    Screen.NKO,
    Screen.ExternalLink
)
