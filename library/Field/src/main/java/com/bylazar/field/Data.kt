package com.bylazar.field

import java.util.UUID

enum class DrawablesTypes {
    CIRCLE,
    RECTANGLE,
    LINE,
    IMAGE
}

sealed class Drawable(
    var type: DrawablesTypes
)

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
) : Drawable(DrawablesTypes.CIRCLE)

data class Rectangle(
    val x: Double,
    val y: Double,
    val w: Double,
    val h: Double,
    val style: Style
) : Drawable(DrawablesTypes.RECTANGLE)

data class Line(
    val x1: Double,
    val y1: Double,
    val x2: Double,
    val y2: Double,
    val style: Style
) : Drawable(DrawablesTypes.LINE)

data class Image(
    val x: Double,
    val y: Double,
    val w: Double,
    val h: Double,
    val id: UUID
) : Drawable(DrawablesTypes.IMAGE)

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

    fun resetOffsets() {
        offsetX = 0.0
        offsetY = 0.0
        rotation = CanvasRotation.DEG_0
    }
}