package com.bylazar.field

import java.util.UUID

sealed interface Drawable

enum class CanvasRotation {
    DEG_0,
    DEG_90,
    DEG_180,
    DEG_270
}

fun interface CanvasPreset {
    fun apply(canvas: Canvas)
}

data class Style(
    val fill: String,
    val outlineFill: String,
    val outlineWidth: Double,
)

data class Circle(
    val x: Double,
    val y: Double,
    val r: Double,
    val style: Style
) : Drawable

data class Rectangle(
    val x: Double,
    val y: Double,
    val w: Double,
    val h: Double,
    val style: Style
) : Drawable

data class Line(
    val x1: Double,
    val y1: Double,
    val x2: Double,
    val y2: Double,
    val style: Style
) : Drawable

data class Image(
    val x: Double,
    val y: Double,
    val w: Double,
    val h: Double,
    val id: UUID
) : Drawable

data class Canvas(
    var offsetX: Double = 0.0,
    var offsetY: Double = 0.0,
    var rotation: CanvasRotation = CanvasRotation.DEG_0,
    var items: MutableList<Drawable> = mutableListOf(),
    var bgID: UUID? = null
) {

    fun reset() {
        items.clear()
    }
}