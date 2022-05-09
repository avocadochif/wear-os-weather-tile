package com.avocadochif.wear.weather.entity.weather

import android.os.Parcelable
import com.avocadochif.wear.weather.entity.temp.Temperature
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DailyWeather(

    @SerializedName("temp")
    var temp: Temperature? = null

) : Weather(), Parcelable
