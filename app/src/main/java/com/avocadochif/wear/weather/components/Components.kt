package com.avocadochif.wear.weather.components

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.wear.tiles.*
import androidx.wear.tiles.DimensionBuilders.expand
import androidx.wear.tiles.DimensionBuilders.wrap
import androidx.wear.tiles.LayoutElementBuilders.HORIZONTAL_ALIGN_CENTER
import androidx.wear.tiles.LayoutElementBuilders.VERTICAL_ALIGN_CENTER
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

fun Spacer(
    context: Context,
    @DimenRes widthResId: Int? = null,
    @DimenRes heightResId: Int? = null
) = LayoutElementBuilders.Spacer.Builder()
    .setWidth(DimensionBuilders.dp(widthResId?.let { context.resources.getDimension(it) } ?: run { 0f }))
    .setHeight(DimensionBuilders.dp(heightResId?.let { context.resources.getDimension(it) } ?: run { 0f }))
    .build()

fun Column(
    @LayoutElementBuilders.HorizontalAlignment horizontalAlignment: Int
) = LayoutElementBuilders.Column.Builder()
    .setHorizontalAlignment(horizontalAlignment)

fun Row(
    @LayoutElementBuilders.VerticalAlignment verticalAlignment: Int
) = LayoutElementBuilders.Row.Builder()
    .setVerticalAlignment(verticalAlignment)

fun CenteredBox(
    context: Context,
    @DimenRes paddingResId: Int? = null,
    content: LayoutElementBuilders.LayoutElement
) = LayoutElementBuilders.Box.Builder()
    .setWidth(expand())
    .setHeight(expand())
    .setModifiers(
        ModifiersBuilders.Modifiers.Builder()
            .setPadding(
                ModifiersBuilders.Padding.Builder()
                    .setAll(
                        DimensionBuilders.dp(paddingResId?.let { context.resources.getDimension(it) } ?: run { 8f })
                    ).build()
            ).build()
    )
    .setHorizontalAlignment(HORIZONTAL_ALIGN_CENTER)
    .setVerticalAlignment(VERTICAL_ALIGN_CENTER)
    .addContent(content)
