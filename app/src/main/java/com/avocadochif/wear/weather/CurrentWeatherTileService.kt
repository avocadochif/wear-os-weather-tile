package com.avocadochif.wear.weather

import android.text.format.DateUtils
import android.util.Log
import androidx.wear.tiles.*
import androidx.wear.tiles.LayoutElementBuilders.HORIZONTAL_ALIGN_CENTER
import androidx.wear.tiles.LayoutElementBuilders.Layout
import androidx.wear.tiles.ResourceBuilders.Resources
import androidx.wear.tiles.TileBuilders.Tile
import androidx.wear.tiles.TimelineBuilders.Timeline
import androidx.wear.tiles.TimelineBuilders.TimelineEntry
import com.avocadochif.wear.weather.components.*
import com.avocadochif.wear.weather.datasource.sharedpreferences.CorePreferences
import com.avocadochif.wear.weather.extensions.getScreenDensity
import com.avocadochif.wear.weather.extensions.getScreenHeightDp
import com.avocadochif.wear.weather.extensions.getScreenShapeType
import com.avocadochif.wear.weather.extensions.getScreenWidthDp
import com.avocadochif.wear.weather.networking.Result
import com.avocadochif.wear.weather.networking.repository.OneCallWeatherRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.guava.future
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CurrentWeatherTileService : TileService() {

    @Inject
    lateinit var preferences: CorePreferences

    @Inject
    lateinit var serviceScope: CoroutineScope

    @Inject
    lateinit var repository: OneCallWeatherRepository

    private val isWeatherDataValid: Boolean by lazy {
        (System.currentTimeMillis() - preferences.oneCallWeatherLastUpdateAt) <= FRESHNESS_DATA_VALID_INTERVAL
    }

    override fun onTileRequest(requestParams: RequestBuilders.TileRequest) = serviceScope.future {
        when (requestParams.state?.lastClickableId) {
            "id_refresh" -> serviceScope.launch {
                when (val result = repository.getOneCallWeather()) {
                    is Result.Success -> result.data?.let {
                        preferences.oneCallWeatherResponse = it
                        preferences.oneCallWeatherLastUpdateAt = System.currentTimeMillis()
                    }
                    is Result.Error -> Log.e("TAG", "${result.apiError.errorType}")
                }
            }
            else -> {}
        }

        Tile.Builder()
            .setResourcesVersion(RESOURCES_VERSION)
            .setFreshnessIntervalMillis(FRESHNESS_INTERVAL)
            .setTimeline(
                Timeline.Builder()
                    .addTimelineEntry(
                        TimelineEntry.Builder()
                            .setLayout(
                                when (isWeatherDataValid) {
                                    true -> getValidDataStateLayout()
                                    false -> getInvalidDataStateLayout()
                                }
                            ).build()
                    ).build()
            ).build()
    }

    private fun getValidDataStateLayout(): Layout {
        val location = preferences.oneCallWeatherResponse?.location ?: "Lviv"
        val temp = preferences.oneCallWeatherResponse?.current?.temp ?: 0
        val weather = preferences.oneCallWeatherResponse?.current?.weather?.firstOrNull()?.main ?: "Rain"

        return Layout.Builder()
            .setRoot(
                CenteredBox(
                    context = this,
                    paddingResId = R.dimen.padding_8dp,
                    content = Column(HORIZONTAL_ALIGN_CENTER)
                        .addContent(
                            Text(
                                context = this,
                                text = location,
                                textColorResId = R.color.violet_1
                            )
                        )
                        .addContent(
                            Spacer(
                                context = this,
                                widthResId = null,
                                heightResId = R.dimen.spacer_4dp
                            )
                        )
                        .addContent(
                            Text(
                                context = this,
                                text = temp.toString(),
                                textColorResId = R.color.white_1
                            )
                        ).addContent(
                            Spacer(
                                context = this,
                                widthResId = null,
                                heightResId = R.dimen.spacer_4dp
                            )
                        )
                        .addContent(
                            Text(
                                context = this,
                                text = weather,
                                textColorResId = R.color.violet_1
                            )
                        ).build()
                ).build()
            ).build()
    }

    private fun getInvalidDataStateLayout(): Layout {
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

    override fun onResourcesRequest(requestParams: RequestBuilders.ResourcesRequest) = serviceScope.future {
        Resources.Builder()
            .setVersion(RESOURCES_VERSION)
            .build()
    }

    companion object {
        private const val RESOURCES_VERSION = "1"
        private const val FRESHNESS_INTERVAL = 15 * DateUtils.MINUTE_IN_MILLIS
        private const val FRESHNESS_DATA_VALID_INTERVAL = 60 * DateUtils.MINUTE_IN_MILLIS
    }

}