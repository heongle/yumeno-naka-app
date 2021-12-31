package com.yumenonaka.ymnkapp.data

import android.content.Context
import com.yumenonaka.ymnkapp.R
import com.yumenonaka.ymnkapp.models.app.ExternalSite

fun getSites(context: Context): List<ExternalSite> {
    return listOf(
        ExternalSite(context.getString(R.string.twitter), "https://twitter.com/Yumeno_Shiori"),
        ExternalSite(context.getString(R.string.youtube), "https://www.youtube.com/channel/UCH0ObmokE-zUOeihkKwWySA"),
        ExternalSite(context.getString(R.string.bilibili), "https://space.bilibili.com/372984197/"),
        ExternalSite(context.getString(R.string.twitcasting_old), "https://ja.twitcasting.tv/c:yumeno_shiori"),
        ExternalSite(context.getString(R.string.twitcasting_new), "https://ja.twitcasting.tv/yumeno_shiori"),
        ExternalSite(context.getString(R.string.niconico), "https://ch.nicovideo.jp/yumenoshiori"),
        ExternalSite(context.getString(R.string.fc2), "https://live.fc2.com/78847652/"),
        ExternalSite(context.getString(R.string.fanbox_r18), "https://www.pixiv.net/fanbox/creator/34204821"),
        ExternalSite(context.getString(R.string.fanbox_all), "https://www.pixiv.net/fanbox/creator/31866176"),
        ExternalSite(context.getString(R.string.cien), "https://ci-en.dlsite.com/creator/4448"),
        ExternalSite(context.getString(R.string.booth), "https://yumenoshiori.booth.pm/"),
        ExternalSite(context.getString(R.string.fantia), "https://fantia.jp/fanclubs/198195"),
    )
}
