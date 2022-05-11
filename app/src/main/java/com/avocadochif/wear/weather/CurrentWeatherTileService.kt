package com.avocadochif.wear.weather

import android.text.format.DateUtils
import android.util.Log
import androidx.wear.tiles.*
import androidx.wear.tiles.LayoutElementBuilders.*
import androidx.wear.tiles.ResourceBuilders.Resources
import androidx.wear.tiles.TileBuilders.Tile
import androidx.wear.tiles.TimelineBuilders.Timeline
import androidx.wear.tiles.TimelineBuilders.TimelineEntry
import com.avocadochif.wear.weather.components.Chip
import com.avocadochif.wear.weather.components.ExpandColumn
import com.avocadochif.wear.weather.components.ExpandRow
import com.avocadochif.wear.weather.components.Text
import com.avocadochif.wear.weather.datasource.sharedpreferences.CorePreferences
import com.avocadochif.wear.weather.extensions.getScreenDensity
import com.avocadochif.wear.weather.extensions.getScreenHeightDp
import com.avocadochif.wear.weather.extensions.getScreenShapeType
import com.avocadochif.wear.weather.extensions.getScreenWidthDp
import com.avocadochif.wear.weather.networking.repository.OneCallWeatherRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.guava.future
import javax.inject.Inject

@AndroidEntryPoint
class CurrentWeatherTileService : TileService() {

    @Inject
    lateinit var preferences: CorePreferences

    @Inject
    lateinit var serviceScope: CoroutineScope

    @Inject
    lateinit var repository: OneCallWeatherRepository

    override fun onTileRequest(requestParams: RequestBuilders.TileRequest) = serviceScope.future {
        if (requestParams.state?.lastClickableId == "id_refresh") {
            Log.e("TAG", "refresh clicked")
        }

        Tile.Builder()
            .setResourcesVersion(RESOURCES_VERSION)
            .setFreshnessIntervalMillis(FRESHNESS_INTERVAL)
            .setTimeline(
                Timeline.Builder()
                    .addTimelineEntry(
                        TimelineEntry.Builder()
                            .setLayout(
                                when (preferences.oneCallWeatherLastUpdateAt != 0L) {
                                    true -> getEmptyStateLayout()
                                    false -> getLoadedStateLayout()
                                }
                            ).build()
                    ).build()
            ).build()
    }

    private fun getEmptyStateLayout(): Layout {
        return Layout.Builder()
            .setRoot(
                Chip(
                    context = this,
                    textResId = R.string.empty_state_refresh,
                    backgroundColorResId = R.color.violet_1,
                    textColorResId = R.color.black_1,
                    clickable = ModifiersBuilders.Clickable.Builder()
                        .setId("id_refresh")
                        .setOnClick(ActionBuilders.LoadAction.Builder().build())
                        .build(),
                    deviceParameters = DeviceParametersBuilders.DeviceParameters.Builder()
                        .setDevicePlatform(DeviceParametersBuilders.DEVICE_PLATFORM_WEAR_OS)
                        .setScreenShape(this.getScreenShapeType())
                        .setScreenDensity(this.getScreenDensity())
                        .setScreenWidthDp(this.getScreenWidthDp())
                        .setScreenHeightDp(this.getScreenHeightDp())
                        .build()
                )
            ).build()
    }

    private fun getLoadedStateLayout(): Layout {
        val location = preferences.oneCallWeatherResponse?.location ?: "Lviv"
        val weather = preferences.oneCallWeatherResponse?.current?.weather?.firstOrNull()?.main ?: "Rain"

        return Layout.Builder()
            .setRoot(
                ExpandColumn(HORIZONTAL_ALIGN_CENTER)
                    .addContent(
                        Text(
                            context = this,
                            text = location,
                            textColorResId = R.color.violet_1
                        )
                    )
                    .addContent(
                        ExpandRow(VERTICAL_ALIGN_CENTER).build()
                    ).addContent(
                        Text(
                            context = this,
                            text = weather,
                            textColorResId = R.color.violet_1
                        )
                    ).build()
            ).build()
    }

    override fun onResourcesRequest(requestParams: RequestBuilders.ResourcesRequest) = serviceScope.future {
        Resources.Builder()
            .setVersion(RESOURCES_VERSION)
            .build()
    }

    companion object {
        private const val RESOURCES_VERSION = "1"
        private const val FRESHNESS_INTERVAL = 15 * DateUtils.MINUTE_IN_MILLIS
    }

}