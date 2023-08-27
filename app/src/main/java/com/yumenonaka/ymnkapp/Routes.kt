package com.yumenonaka.ymnkapp

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContentScope
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
    val content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    data object Schedule : Screen(
        route = "schedule",
        resourceId = R.string.schedule,
        content = { Schedule() }
    )

    data object WeeklySchedule : Screen(
        route = "weekly-schedule",
        resourceId = R.string.weekly_schedule,
        content = { WeeklySchedule() })

    data object BirthdayCountdown : Screen(
        route = "birthday-countdown",
        resourceId = R.string.birthday_countdown,
        content = { BirthdayCounter() })

    data object Soine : Screen(
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

    data object SoundEffect : Screen(
        route = "sound-effect",
        resourceId = R.string.sound_effect,
        content = {
            SoundEffectButtons(
                listOf(
                    Pair(R.string.sound_effect, soundEffects),
                    Pair(R.string.kushami, kushamiEffects),
                    Pair(R.string.nko, nkoEffects)
                )
            )
        }
    )

    data object ExternalLink : Screen(
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
    Screen.ExternalLink
)
