package com.bylazar.field

import java.util.UUID

class FieldManager(
    config: FieldPluginConfig,
    private val sendCanvas: (canvas: Canvas) -> Unit,
    private val sendImages: (images: MutableMap<UUID, String>) -> Unit
) {
    var images: MutableMap<UUID, String> = mutableMapOf()

    var disableDefaultBg = false
    val defaultBgID = registerImage(config.defaultBg)

    val updateInterval = config.canvasUpdateInterval
    var lastUpdate = 0L
    val timeSinceLastUpdate: Long
        get() = System.currentTimeMillis() - lastUpdate
    val shouldUpdateCanvas: Boolean
        get() = timeSinceLastUpdate >= updateInterval


    var canvas = Canvas()
    var lastCanvas = Canvas()

    init {
        if (!disableDefaultBg){
            canvas.bgID = defaultBgID
            lastCanvas.bgID = defaultBgID
        }
    }

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

    fun moveCursor(x: Double, y: Double) {
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

    fun setStyle(style: Style){
        setFill(style.fill)
        setOutline(style.outlineFill, style.outlineWidth)
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
        canvas.items.add(
            Circle(
                cursorX,
                cursorY,
                r,
                currentStyle
            )
        )
    }

    fun line(x2: Double, y2: Double) {
        canvas.items.add(
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
        canvas.items.add(
            Rectangle(
                cursorX,
                cursorY,
                w, h,
                currentStyle
            )
        )
    }

    fun registerImage(preset: ImagePreset): UUID {
        return registerBase64Image(preset.get())
    }

    fun registerBase64Image(base64: String): UUID {
        val existingEntry = images.entries.find { it.value == base64 }
        if (existingEntry != null) return existingEntry.key

        val id = UUID.randomUUID()
        images[id] = base64

        sendImages(images)

        return id
    }

    fun registerImage(path: String): UUID {
        val base64 = FieldImages().loadResourceAsBase64(path)
        return registerBase64Image(base64)
    }

    fun img(w: Double, h: Double, id: UUID) {
        canvas.items.add(
            Image(
                cursorX,
                cursorY,
                w, h,
                id
            )
        )
    }

    fun setBase64Background(base64: String) {
        val id = registerBase64Image(base64)
        setBackground(id)
    }

    fun setBackground(path: String) {
        val base64 = FieldImages().loadResourceAsBase64(path)
        setBase64Background(base64)
    }

    fun setBackground(id: UUID) {
        canvas.bgID = id
    }

    fun setBackground(image: ImagePreset) {
        setBase64Background(image.get())
    }

    fun update() {
        if (!disableDefaultBg && canvas.bgID == null) {
            canvas.bgID = defaultBgID
        }
        if (shouldUpdateCanvas) {
            sendCanvas(canvas)
            lastUpdate = System.currentTimeMillis()

            lastCanvas.bgID = canvas.bgID
            lastCanvas.items = canvas.items.toList().toMutableList()
            lastCanvas.offsetX = canvas.offsetX
            lastCanvas.offsetY = canvas.offsetY
            lastCanvas.rotation = canvas.rotation
        }

        canvas.reset()
        cursorX = 0.0
        cursorY = 0.0
        cursorHeading = 0.0
    }

    fun setOffsets(o: CanvasPreset) {
        o.apply(canvas)
    }
}