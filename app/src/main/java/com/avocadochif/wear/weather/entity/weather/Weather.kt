package com.avocadochif.wear.weather.entity.weather

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
open class Weather(

    @SerializedName("dt")
    var timestamp: Long? = null,

    @SerializedName("sunrise")
    var sunriseAt: Long? = null,

    @SerializedName("sunset")
    var sunsetAt: Long? = null,

    @SerializedName("pressure")
    var pressure: Int? = null,

    @SerializedName("humidity")
    var humidity: Int? = null,

    @SerializedName("uvi")
    var uvIndex: Double? = null,

    @SerializedName("clouds")
    var clouds: Int? = null, //amount of clouds in percents

    @SerializedName("wind_speed")
    var speedOfWind: Double? = null, //meters per seconds

    @SerializedName("weather")
    var weather: List<WeatherDetails> = mutableListOf() //single item list with weather icon id, description, etc.

) : Parcelable
