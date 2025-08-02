package com.bylazar.field

import com.bylazar.panels.Panels

object PanelsField {
    val pkgName = this::class.java.`package`?.name ?: "null"

    fun getField(): FieldManager {
        val plugin = Panels.getPlugin(pkgName) as Plugin
        return plugin.manager
    }

    val PANELS = CanvasPreset { /* no-op */ }
    val PEDRO_PATHING = CanvasPreset {
        it.offsetX = -24.0 * 3
        it.offsetY = 24.0 * 3
        it.rotation = CanvasRotation.DEG_270
    }
    val ROAD_RUNNER = CanvasPreset { /* no-op */ }

    val TRANSPARENT = "transparent"
    val WHITE = "white"
    val BLACK = "black"
    val RED = "red"
    val BLUE = "blue"

    val images = FieldImages()
}