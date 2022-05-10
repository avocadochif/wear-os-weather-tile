package com.avocadochif.wear.weather.networking.repository

import com.avocadochif.wear.weather.BuildConfig
import com.avocadochif.wear.weather.networking.OpenWeatherMapRequest
import com.avocadochif.wear.weather.networking.baseResponseHandler
import com.avocadochif.wear.weather.networking.safeApiCall

class OneCallWeatherRepository(
    private val service: OpenWeatherMapRequest.OneCallAPI
) {

    suspend fun getOneCallWeather() = safeApiCall {
        service.getOneCallWeather(
            latitude = 49.842957,
            longitude = 24.031111,
            exclude = "minutely,hourly,alerts",
            appID = BuildConfig.APP_ID
        ).baseResponseHandler()
    }

}