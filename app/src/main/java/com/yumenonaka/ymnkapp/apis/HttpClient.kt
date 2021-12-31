package com.yumenonaka.ymnkapp.apis

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

object HttpClient {
    private val client: OkHttpClient = OkHttpClient()
    fun execute(request: Request): String {
        val response: Response = client.newCall(request).execute()
        return response.body!!.string()
    }
}
