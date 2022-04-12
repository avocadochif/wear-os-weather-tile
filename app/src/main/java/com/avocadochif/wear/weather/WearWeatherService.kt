package com.avocadochif.wear.weather

import android.graphics.Color
import androidx.wear.tiles.ColorBuilders.ColorProp
import androidx.wear.tiles.DimensionBuilders.expand
import androidx.wear.tiles.LayoutElementBuilders.*
import androidx.wear.tiles.RequestBuilders
import androidx.wear.tiles.ResourceBuilders.Resources
import androidx.wear.tiles.TileBuilders.Tile
import androidx.wear.tiles.TileService
import androidx.wear.tiles.TimelineBuilders.Timeline
import androidx.wear.tiles.TimelineBuilders.TimelineEntry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.guava.future

class WearWeatherService : TileService() {

    private val serviceScope = CoroutineScope(Dispatchers.IO)

    override fun onTileRequest(requestParams: RequestBuilders.TileRequest) = serviceScope.future {
        Tile.Builder()
            .setResourcesVersion(RESOURCES_VERSION)
            .setFreshnessIntervalMillis(FRESHNESS_INTERVAL)
            .setTimeline(
                Timeline.Builder()
                    .addTimelineEntry(
                        TimelineEntry.Builder()
                            .setLayout(
                                layout()
                            ).build()
                    ).build()
            ).build()
    }

    override fun onResourcesRequest(requestParams: RequestBuilders.ResourcesRequest) = serviceScope.future {
        Resources.Builder()
            .setVersion(RESOURCES_VERSION)
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    private fun layout(): Layout {
        return Layout.Builder()
            .setRoot(
                Box.Builder()
                    .setHeight(expand())
                    .setWidth(expand())
                    .setVerticalAlignment(VERTICAL_ALIGN_CENTER)
                    .setHorizontalAlignment(HORIZONTAL_ALIGN_CENTER)
                    .addContent(
                        Text.Builder()
                            .setText("Hello World!!!")
                            .setFontStyle(
                                FontStyle.Builder()
                                    .setColor(
                                        ColorProp.Builder()
                                            .setArgb(Color.argb(255, 255, 0, 255))
                                            .build()
                                    ).build()
                            ).build()
                    ).build()
            ).build()
    }

    companion object {
        private const val RESOURCES_VERSION = "1"
        private const val FRESHNESS_INTERVAL = 15 * 60 * 1000L //15m
    }

}