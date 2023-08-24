package com.xplor.android.challenge.utils

sealed class ApiState<out T> {

    data class Success<out T>(val data: T) : ApiState<T>()

    object Loading : ApiState<Nothing>()

    data class Error(val e: Throwable, val msg: String?) : ApiState<Nothing>()

}
