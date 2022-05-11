package com.avocadochif.wear.weather.entity.response

import android.os.Parcelable
import com.avocadochif.wear.weather.entity.weather.CurrentWeather
import com.avocadochif.wear.weather.entity.weather.DailyWeather
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class OneCallWeatherResponse(

    @SerializedName("lat")
    var latitude: Double? = null,

    @SerializedName("lon")
    var longitude: Double? = null,

    @SerializedName("current")
    var current: CurrentWeather? = null,

    @SerializedName("daily")
    var daily: List<DailyWeather> = mutableListOf(),

    @Transient
    var location: String = "Lviv" //TODO: hardcoded, change later

) : Parcelable
