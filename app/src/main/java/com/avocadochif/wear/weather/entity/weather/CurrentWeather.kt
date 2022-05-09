package com.avocadochif.wear.weather.entity.weather

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrentWeather(

    @SerializedName("temp")
    var temp: Double? = null //temperature in kelvin

) : Weather(), Parcelable
