package com.bylazar.field

object FieldPresets {
    val PANELS = FieldPresetParams(
        name = "Panels",
        flipY = true
    )
    val PEDRO_PATHING = FieldPresetParams(
        name = "Pedro Pathing",
        offsetX = 24.0 * -3,
        offsetY = 24.0 * 3,
        rotation = CanvasRotation.DEG_90,
    )

    val ROAD_RUNNER = FieldPresetParams(
        name = "Road Runner",
        flipY = true
    )

    val allPresets: List<FieldPresetParams> = listOf(
        PANELS, PEDRO_PATHING, ROAD_RUNNER
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