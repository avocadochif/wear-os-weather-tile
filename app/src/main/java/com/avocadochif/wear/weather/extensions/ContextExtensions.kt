package com.avocadochif.wear.weather.extensions

import android.content.Context
import androidx.wear.tiles.DeviceParametersBuilders
import kotlin.math.roundToInt

fun Context.getScreenShapeType(): Int {
    return when(this.resources.configuration.isScreenRound) {
        true -> DeviceParametersBuilders.SCREEN_SHAPE_ROUND
        false -> DeviceParametersBuilders.SCREEN_SHAPE_RECT
    }
}

fun Context.getScreenDensity(): Float {
    return this.resources.displayMetrics.density
}

fun Context.getScreenWidthDp(): Int {
    return (this.resources.displayMetrics.widthPixels / this.resources.displayMetrics.density).roundToInt()
}

fun Context.getScreenHeightDp(): Int {
    return (this.resources.displayMetrics.heightPixels / this.resources.displayMetrics.density).roundToInt()
}