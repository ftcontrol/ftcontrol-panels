package com.bylazar.field

object FieldPresets {
    val PANELS = FieldPresetParams(
        name = "Panels",
        reverseXY = true,
    )
    val DEFAULT_FTC = FieldPresetParams(
        name = "Default FTC",
        reverseXY = true,
    )
    val PEDRO_PATHING = FieldPresetParams(
        name = "Pedro Pathing",
        offsetX = 24.0 * -3,
        offsetY = 24.0 * -3,
        flipX = true,
        reverseXY = false,
        rotation = CanvasRotation.DEG_90,
    )

    val ROAD_RUNNER = FieldPresetParams(
        name = "Road Runner",
        reverseXY = true,
    )

    val allPresets: List<FieldPresetParams> = listOf(
        PANELS, DEFAULT_FTC, PEDRO_PATHING, ROAD_RUNNER
    )
}

object PanelsField {
    val field: FieldManager
        get() = Plugin.manager

    val presets = FieldPresets

    val TRANSPARENT = "transparent"
    val WHITE = "white"
    val BLACK = "black"
    val RED = "red"
    val BLUE = "blue"

    val images = FieldImages()
}