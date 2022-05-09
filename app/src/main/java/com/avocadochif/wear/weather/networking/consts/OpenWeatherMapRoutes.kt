package com.avocadochif.wear.weather.networking.consts

object OpenWeatherMapRoutes {

    /////////////////////////////////////////////////////////
    ///////////////////// ONE CALL API //////////////////////
    /////////////////////////////////////////////////////////

    const val ONE_CALL_WEATHER = "/data/2.5/onecall" +
        "?lat={latitude}" +
        "&lon={longitude}" +
        "&exclude={listOfForecastExcluding}" +
        "&appid={appID}"

}