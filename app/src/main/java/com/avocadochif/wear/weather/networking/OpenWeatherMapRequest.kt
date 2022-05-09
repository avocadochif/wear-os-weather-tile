package com.avocadochif.wear.weather.networking

import com.avocadochif.wear.weather.entity.response.OneCallWeatherResponse
import com.avocadochif.wear.weather.networking.consts.OpenWeatherMapRoutes.ONE_CALL_WEATHER
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface OpenWeatherMapRequest {

    /////////////////////////////////////////////////////////
    ///////////////////// ONE CALL API //////////////////////
    /////////////////////////////////////////////////////////

    interface OneCallAPI {

        @POST(ONE_CALL_WEATHER)
        suspend fun getOneCallWeather(
            @Query("lat") latitude: Double, //49.842957
            @Query("lon") longitude: Double, //24.031111
            @Query("exclude") exclude: String, //minutely,hourly,alerts
            @Query("appid") appID: String
        ): Response<OneCallWeatherResponse>

    }

}