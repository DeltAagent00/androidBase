package com.homedev.weather.api.network.repositories

import com.homedev.weather.api.network.Result
import java.lang.Exception

/**
 * Created by Alexandr Zheleznyakov on 2019-10-08.
 */
abstract class BaseRepository() {
    suspend fun <T: Any> safeApiCall(call: suspend () -> T): Result<T> {
        val result: Result<T> = safeApiResult(call)
        return when(result) {
            is Result.Success ->
                result
            is Result.Error -> {
                Result.Error(result.exception)
            }
        }
    }

    private suspend fun <T: Any> safeApiResult(call: suspend () -> T): Result<T> {
        return try {
            Result.Success(call.invoke())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}