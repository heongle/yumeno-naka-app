package com.yumenonaka.ymnkapp.screens.links

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.yumenonaka.ymnkapp.components.CardVisitPage
import com.yumenonaka.ymnkapp.data.getSites

@Composable
fun Links() {
    val context = LocalContext.current
    val sites = getSites(context)
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        sites.forEach{
            CardVisitPage(title = it.name, url = it.url)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestLinks() {
    Links()
}
