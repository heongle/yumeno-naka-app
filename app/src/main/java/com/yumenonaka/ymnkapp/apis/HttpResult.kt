package com.yumenonaka.ymnkapp.apis

sealed interface HttpResult<out T> {
    data class Success<T>(val data: T): HttpResult<T>
    data class Error(val message: String): HttpResult<Nothing>
    data object Loading: HttpResult<Nothing>
}
