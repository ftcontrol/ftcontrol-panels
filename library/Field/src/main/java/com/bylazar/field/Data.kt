package com.bylazar.field

import org.opencv.imgproc.Imgproc.line


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
)

data class Rectangle(
    val x: Double,
    val y: Double,
    val w: Double,
    val h: Double,
    val style: Style
)

data class Line(
    val x1: Double,
    val y1: Double,
    val x2: Double,
    val y2: Double,
    val style: Style
)

data class Canvas(
    var offsetX: Double = 0.0,
    var offsetY: Double = 0.0,
    var rotation: CanvasRotation = CanvasRotation.DEG_0,
    var circles: MutableList<Circle> = mutableListOf(),
    var rectangles: MutableList<Rectangle> = mutableListOf(),
    var lines: MutableList<Line> = mutableListOf(),
) {

    fun reset() {
        circles = mutableListOf()
        rectangles = mutableListOf()
        lines = mutableListOf()
    }
}