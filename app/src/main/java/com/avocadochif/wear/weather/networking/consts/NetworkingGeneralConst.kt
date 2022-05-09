package com.avocadochif.wear.weather.networking.consts

import android.text.format.DateUtils

object NetworkingGeneralConst {

    ///////////////////////////// Api client //////////////////////////////////

    const val CONTENT_TYPE_HEADER = "application/json"
    const val CONTENT_TYPE_HEADER_NAME = "Content-Type"
    const val ACCEPT_HEADER = "application/json"
    const val ACCEPT_HEADER_NAME = "Accept"
    const val READ_WRITE_TIMEOUT: Long = 30 * DateUtils.SECOND_IN_MILLIS
    const val CONNECTION_TIMEOUT: Long = 20 * DateUtils.SECOND_IN_MILLIS
    const val MAX_CONTENT_LENGTH: Long = 250000L

    /////////////////////////////// Headers ///////////////////////////////////

    //////////////////////////////// Paths ////////////////////////////////////

    //////////////////////////////// Parts ////////////////////////////////////

    //////////////////////////////// Queries //////////////////////////////////

    const val latitude = "{latitude}" //49.842957
    const val longitude = "{longitude}" //24.031111
    const val listOfForecastForExcluding = "{listOfForecastForExcluding}" //minutely,hourly,alerts
    const val appID = "{appID}"

}