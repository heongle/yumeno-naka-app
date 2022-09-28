package com.yumenonaka.ymnkapp

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.*
import com.yumenonaka.ymnkapp.data.DeepLink
import com.yumenonaka.ymnkapp.screens.birthdaycounter.BirthdayCounter
import com.yumenonaka.ymnkapp.screens.links.Links
import com.yumenonaka.ymnkapp.screens.schedule.Schedule
import com.yumenonaka.ymnkapp.screens.soine.Soine
import com.yumenonaka.ymnkapp.screens.soine.rememberSoineState
import com.yumenonaka.ymnkapp.screens.soundeffect.SoundEffectButtons
import com.yumenonaka.ymnkapp.screens.soundeffect.kushamiEffects
import com.yumenonaka.ymnkapp.screens.soundeffect.nkoEffects
import com.yumenonaka.ymnkapp.screens.soundeffect.soundEffects
import com.yumenonaka.ymnkapp.screens.weeklyschedule.WeeklySchedule

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    val needDivide: Boolean = false,
    val deepLink: List<NavDeepLink> = emptyList(),
    val content: @Composable (NavBackStackEntry) -> Unit,
) {
    object Schedule : Screen(
        route = "schedule",
        resourceId = R.string.schedule,
        content = { Schedule() }
    )
    object WeeklySchedule : Screen(
        route = "weekly-schedule",
        resourceId = R.string.weekly_schedule,
        content = { WeeklySchedule() })
    object BirthdayCountdown : Screen(
        route = "birthday-countdown",
        resourceId = R.string.birthday_countdown,
        content = { BirthdayCounter() })
    object Soine : Screen(
        route = "soine",
        resourceId = R.string.soine_player,
        deepLink = listOf(
            navDeepLink { uriPattern = "${DeepLink.SOINE}?playing={playing}" }
        ),
        content = { backStackEntry ->
            val initPlaying = backStackEntry.arguments?.getString("playing") == "true"
            Soine(soineState = rememberSoineState(context = LocalContext.current, initPlaying = initPlaying))
        },
        needDivide = true
    )
    object SoundEffect : Screen(
        route = "sound-effect",
        resourceId = R.string.sound_effect,
        content = { SoundEffectButtons(soundEffects) }
    )
    object Kushami : Screen(
        route = "sound-effect-kushami",
        resourceId = R.string.kushami,
        content = { SoundEffectButtons(kushamiEffects) }
    )
    object NKO : Screen(
        route = "sound-effect-nko",
        resourceId = R.string.nko,
        content = { SoundEffectButtons(nkoEffects) },
        needDivide = true
    )
    object ExternalLink : Screen(
        route = "external-links",
        resourceId = R.string.external_link,
        content = { Links() }
    )
}

val routes = listOf(
    Screen.Schedule,
    Screen.WeeklySchedule,
    Screen.BirthdayCountdown,
    Screen.Soine,
    Screen.SoundEffect,
    Screen.Kushami,
    Screen.NKO,
    Screen.ExternalLink
)
