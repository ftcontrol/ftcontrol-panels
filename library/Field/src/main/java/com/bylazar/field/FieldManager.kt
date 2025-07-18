package com.bylazar.field

class FieldManager(
    val config: FieldPluginConfig,
    private val sendCanvas: (canvas: Canvas) -> Unit
) {
    var canvas = Canvas()

    var cursorX = 0.0
    var cursorY = 0.0
    var cursorHeading = 0.0
    var currentFill = PanelsField.WHITE
    var currentOutlineFill = PanelsField.TRANSPARENT
    var currentOutlineWidth = 0.1
    val currentStyle: Style
        get() = Style(
            fill = currentFill,
            outlineFill = currentOutlineFill,
            outlineWidth = currentOutlineWidth
        )

    fun goto(x: Double, y: Double) {
        cursorX = x
        cursorY = y
    }

    fun setFill(fill: String) {
        currentFill = fill
    }

    fun setOutlineFill(fill: String) {
        currentOutlineFill = fill
    }

    fun setOutlineWidth(width: Double) {
        currentOutlineWidth = width
    }

    fun setOutline(fill: String, width: Double) {
        setOutlineFill(fill)
        setOutlineWidth(width)
    }

    fun setStyle(fill: String, outline: String, width: Double) {
        setFill(fill)
        setOutline(outline, width)
    }

    fun clearFill() {
        setFill(PanelsField.TRANSPARENT)
    }

    fun clearOutline() {
        setOutline(PanelsField.TRANSPARENT, 0.0)
    }

    fun clearStyle() {
        clearFill()
        clearOutline()
    }

    fun circle(r: Double) {
        canvas.circles.add(
            Circle(
                cursorX,
                cursorY,
                r,
                currentStyle
            )
        )
    }

    fun line(x2: Double, y2: Double) {
        canvas.lines.add(
            Line(
                cursorX,
                cursorY,
                x2,
                y2,
                currentStyle
            )
        )
    }

    fun rect(w: Double, h: Double) {
        canvas.rectangles.add(
            Rectangle(
                cursorX,
                cursorY,
                w, h,
                currentStyle
            )
        )
    }

    fun update() {
        sendCanvas(canvas)
        canvas.reset()
        cursorX = 0.0
        cursorY = 0.0
        cursorHeading = 0.0
    }

    fun setOffsets(o: CanvasPreset) {
        o.apply(canvas)
    }
}