package com.avocadochif.wear.weather.networking.error

data class ApiError<E: ErrorResponse>(
    var message: String = "",
    var errorType: ErrorType,
    val errorBody: E? = null
)