package com.homedev.weather.api.network

/**
 * Created by Alexandr Zheleznyakov on 2019-06-17.
 */
sealed class Result<out T: Any> {
    data class Success<out T: Any>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
}