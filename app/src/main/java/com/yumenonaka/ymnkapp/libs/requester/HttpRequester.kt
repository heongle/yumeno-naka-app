package com.yumenonaka.ymnkapp.libs.requester

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.yumenonaka.ymnkapp.apis.HttpResult
import io.ktor.client.request.HttpRequestBuilder
import kotlinx.coroutines.delay

@Suppress("MemberVisibilityCanBePrivate")
class HttpRequester<T>(
    val fetcher: suspend (block: HttpRequestBuilder.() -> Unit) -> T,
    val retry: Int = 3,
    val retryDelay: Long = 2500,
) {
    private var retryCount by mutableIntStateOf(0)
    private var isLoadingData by mutableStateOf(false)
    var data by mutableStateOf<HttpResult<T>>(HttpResult.Loading)
        private set

    suspend fun startFetch(errorMessage: (e: Exception) -> String? = { null }, block: HttpRequestBuilder.() -> Unit = {}) {
        isLoadingData = true
        data.let {
            if (it is HttpResult.Success) {
                data = HttpResult.Success(it.data, true)
            }
        }

        try {
            val result = fetcher(block)
            data = HttpResult.Success(result)
        } catch (e: Exception) {
            e.printStackTrace()
            if (retryCount >= retry) {
                data = HttpResult.Error(errorMessage(e) ?: "Unexpected Error")
                retryCount = 0
                throw e
            }
            ++retryCount
            delay(retryDelay)
            startFetch(errorMessage, block)
        } finally {
            isLoadingData = false
        }
    }
}
