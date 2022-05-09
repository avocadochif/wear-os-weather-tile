package com.avocadochif.wear.weather.entity.temp

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Class contains all day temperatures in kelvin
 */

@Parcelize
data class Temperature(

    @SerializedName("day")
    var day: Double? = null,

    @SerializedName("min")
    var min: Double? = null,

    @SerializedName("max")
    var max: Double? = null,

    @SerializedName("night")
    var night: Double? = null,

    @SerializedName("eve")
    var eve: Double? = null,

    @SerializedName("morn")
    var morn: Double? = null

) : Parcelable
