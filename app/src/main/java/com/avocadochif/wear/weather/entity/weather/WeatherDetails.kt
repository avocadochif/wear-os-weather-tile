package com.avocadochif.wear.weather.entity.weather

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherDetails(

    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("main")
    var main: String? = null,

    @SerializedName("description")
    var description: String? = null,

    @SerializedName("icon")
    var icon: String? = null

) : Parcelable
