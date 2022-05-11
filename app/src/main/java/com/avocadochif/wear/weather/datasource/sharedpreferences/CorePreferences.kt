package com.avocadochif.wear.weather.datasource.sharedpreferences

import android.content.SharedPreferences
import androidx.core.content.edit
import com.avocadochif.wear.weather.entity.response.OneCallWeatherResponse
import com.google.gson.Gson

class CorePreferences(
    private val preferences: SharedPreferences,
    private val gson: Gson
) {

    fun clearData() {
        preferences.edit(true) {
            remove(ONE_CALL_WEATHER_LAST_UPDATE_AT)
            remove(ONE_CALL_WEATHER_RESPONSE)
        }
    }

    var oneCallWeatherLastUpdateAt: Long
        get() = preferences.getLong(ONE_CALL_WEATHER_LAST_UPDATE_AT, 0)
        set(value) = preferences.edit().putLong(ONE_CALL_WEATHER_LAST_UPDATE_AT, value).apply()

    var oneCallWeatherResponse: OneCallWeatherResponse?
        get() = gson.fromJson(preferences.getString(ONE_CALL_WEATHER_RESPONSE, ""), OneCallWeatherResponse::class.java)
        set(value) = preferences.edit().putString(ONE_CALL_WEATHER_RESPONSE, gson.toJson(value)).apply()

    companion object {
        private const val ONE_CALL_WEATHER_LAST_UPDATE_AT = "one.call.weather.last.update.at"
        private const val ONE_CALL_WEATHER_RESPONSE = "one.call.weather.response"
    }

}