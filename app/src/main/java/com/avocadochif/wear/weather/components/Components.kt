package com.avocadochif.wear.weather.components

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.wear.tiles.ColorBuilders
import androidx.wear.tiles.DeviceParametersBuilders
import androidx.wear.tiles.DimensionBuilders.expand
import androidx.wear.tiles.DimensionBuilders.wrap
import androidx.wear.tiles.LayoutElementBuilders
import androidx.wear.tiles.LayoutElementBuilders.HORIZONTAL_ALIGN_CENTER
import androidx.wear.tiles.ModifiersBuilders
import androidx.wear.tiles.material.Chip
import androidx.wear.tiles.material.ChipColors

fun Chip(
    context: Context,
    @StringRes textResId: Int,
    @ColorRes backgroundColorResId: Int,
    @ColorRes textColorResId: Int,
    clickable: ModifiersBuilders.Clickable,
    deviceParameters: DeviceParametersBuilders.DeviceParameters
) = Chip.Builder(context, clickable, deviceParameters)
    .setWidth(wrap())
    .setPrimaryTextContent(context.resources.getString(textResId))
    .setHorizontalAlignment(HORIZONTAL_ALIGN_CENTER)
    .setChipColors(
        ChipColors(
            ContextCompat.getColor(context, backgroundColorResId),
            ContextCompat.getColor(context, textColorResId)
        )
    ).build()

fun Text(
    context: Context,
    text: String,
    @ColorRes textColorResId: Int
) = LayoutElementBuilders.Text.Builder()
    .setText(text)
    .setFontStyle(
        LayoutElementBuilders.FontStyle.Builder()
            .setColor(
                ColorBuilders.ColorProp.Builder()
                    .setArgb(ContextCompat.getColor(context, textColorResId))
                    .build()
            ).build()
    ).build()

fun ExpandColumn(
    @LayoutElementBuilders.HorizontalAlignment horizontalAlignment: Int
) = LayoutElementBuilders.Column.Builder()
    .setWidth(expand())
    .setHeight(expand())
    .setHorizontalAlignment(horizontalAlignment)

fun ExpandRow(
    @LayoutElementBuilders.VerticalAlignment verticalAlignment: Int
) = LayoutElementBuilders.Row.Builder()
    .setWidth(expand())
    .setHeight(expand())
    .setVerticalAlignment(verticalAlignment)