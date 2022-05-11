package com.avocadochif.wear.weather

import android.content.ComponentName
import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.wear.tiles.manager.TileUiClient

class WearWeatherPreviewActivity : ComponentActivity() {

    private var tileUiClient: TileUiClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wear_weather_preview)

        tileUiClient = TileUiClient(
            context = this,
            component = ComponentName(this, CurrentWeatherTileService::class.java),
            parentView = findViewById<FrameLayout>(R.id.root)
        ).also {
            it.connect()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        tileUiClient?.close()
    }

}
