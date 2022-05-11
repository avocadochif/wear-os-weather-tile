package com.avocadochif.wear.weather.components

import android.content.Context
import android.graphics.Color
import androidx.wear.tiles.ColorBuilders
import androidx.wear.tiles.DeviceParametersBuilders
import androidx.wear.tiles.DimensionBuilders.wrap
import androidx.wear.tiles.LayoutElementBuilders.HORIZONTAL_ALIGN_CENTER
import androidx.wear.tiles.ModifiersBuilders
import androidx.wear.tiles.material.Chip
import androidx.wear.tiles.material.ChipColors

fun RefreshChip(
    context: Context,
    clickable: ModifiersBuilders.Clickable,
    deviceParameters: DeviceParametersBuilders.DeviceParameters
) = Chip.Builder(context, clickable, deviceParameters)
    .setWidth(wrap())
    .setPrimaryTextContent("Refresh")
    .setContentDescription("refresh weather")
    .setHorizontalAlignment(HORIZONTAL_ALIGN_CENTER)
    .setChipColors(
        ChipColors(
            ColorBuilders.ColorProp.Builder()
                .setArgb(Color.argb(255, 153, 138, 242))
                .build(),
            ColorBuilders.ColorProp.Builder()
                .setArgb(Color.argb(255, 32, 33, 36))
                .build()
        )
    ).build()
