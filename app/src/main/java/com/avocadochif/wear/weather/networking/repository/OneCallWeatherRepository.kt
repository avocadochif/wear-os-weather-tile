package com.avocadochif.wear.weather.networking.repository

import com.avocadochif.wear.weather.networking.OpenWeatherMapRequest
import com.avocadochif.wear.weather.networking.baseResponseHandler
import com.avocadochif.wear.weather.networking.safeApiCall

class OneCallWeatherRepository(
    private val service: OpenWeatherMapRequest.OneCallAPI
) {

    suspend fun getOneCallWeather() = safeApiCall {
        service.getOneCallWeather().baseResponseHandler()
    }

}