package com.avocadochif.wear.weather.networking

import com.avocadochif.wear.weather.networking.error.ApiError
import com.avocadochif.wear.weather.networking.error.ErrorResponse

sealed class Result<out T : Any?> {
    data class Success<out T : Any?>(val data: T?) : Result<T>()
    data class Error(val apiError: ApiError<ErrorResponse>) : Result<Nothing>()
}