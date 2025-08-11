import { Distance, FIELD_HEIGHT, FIELD_WIDTH } from "./primitives"
import { CanvasRotation, rotationToRadians } from "../types";

export let ctx: CanvasRenderingContext2D | null

let canvasRef: HTMLCanvasElement | null = null
let dpr = 1

let appliedOffsetX = new Distance(0)
let appliedOffsetY = new Distance(0)
let appliedOffsetHeading: CanvasRotation = CanvasRotation.DEG_0

const imageCache: Map<string, HTMLImageElement> = new Map()

function applyBaseTransform() {
    if (!ctx) return
    ctx.setTransform(dpr, 0, 0, dpr, 0, 0)
    ctx.translate(FIELD_WIDTH.pixels / 2, FIELD_HEIGHT.pixels / 2)
    ctx.rotate(rotationToRadians(appliedOffsetHeading))
}

function applyOverlayTransform() {
    if (!ctx) return
    ctx.setTransform(dpr, 0, 0, dpr, 0, 0)
    ctx.translate(
        FIELD_WIDTH.pixels / 2 + appliedOffsetX.pixels,
        FIELD_HEIGHT.pixels / 2 + appliedOffsetY.pixels
    )
    ctx.rotate(rotationToRadians(appliedOffsetHeading))
}

export function init(
    canvas: HTMLCanvasElement,
    offsetX: Distance,
    offsetY: Distance,
    offsetHeading: CanvasRotation,
) {
    if (!canvas) return

    const nextDpr = window.devicePixelRatio || 1
    const expectedW = Math.round(FIELD_WIDTH.pixels * nextDpr)
    const expectedH = Math.round(FIELD_HEIGHT.pixels * nextDpr)

    const sameCanvas = canvasRef === canvas && ctx != null
    const sameDpr = nextDpr === dpr
    const sameSize = canvas.width === expectedW && canvas.height === expectedH
    const sameOffset =
        appliedOffsetX.inches === offsetX.inches &&
        appliedOffsetY.inches === offsetY.inches
    const sameHeading = appliedOffsetHeading === offsetHeading

    if (sameCanvas && sameDpr && sameSize && sameOffset && sameHeading) return

    if (!sameCanvas) {
        ctx = canvas.getContext("2d")
        canvasRef = canvas
    }
    dpr = nextDpr

    if (!sameSize) {
        canvas.width = expectedW
        canvas.height = expectedH
    }

    const offsetChanged = !sameOffset
    const headingChanged = !sameHeading
    appliedOffsetX = offsetX
    appliedOffsetY = offsetY
    appliedOffsetHeading = offsetHeading

    if (offsetChanged || headingChanged || !sameSize || !sameCanvas || !sameDpr) {
        applyOverlayTransform()
    }
}

export function setOffset(offsetX: Distance, offsetY: Distance) {
    appliedOffsetX = offsetX
    appliedOffsetY = offsetY
    applyOverlayTransform()
}

export function setRotation(heading: CanvasRotation) {
    appliedOffsetHeading = heading
    applyOverlayTransform()
}

export function clear() {
    if (!ctx || !canvasRef) return
    ctx.save()
    ctx.setTransform(1, 0, 0, 1, 0, 0)
    ctx.clearRect(0, 0, canvasRef.width, canvasRef.height)
    ctx.restore()
}

export async function drawBase64Image(
    key: string,
    base64: string,
    startX: Distance,
    startY: Distance,
    width: Distance,
    height: Distance
) {
    if (!ctx) return

    let img: HTMLImageElement
    if (imageCache.has(key)) {
        img = imageCache.get(key)!
    } else {
        img = new Image()
        if (base64 == undefined) return
        const hasPrefix = base64.startsWith("data:image/png;base64,")
        img.src = hasPrefix ? base64 : `data:image/png;base64,${base64}`
        await img.decode()
        imageCache.set(key, img)
    }

    ctx.save()
    ctx.drawImage(img, startX.pixels, startY.pixels, width.pixels, height.pixels)
    ctx.restore()
}

export async function drawFieldImage(key: string, base64: string) {
    if (!ctx) return
    ctx.save()
    applyBaseTransform()
    await drawBase64Image(
        key,
        base64,
        new Distance(-FIELD_WIDTH.inches / 2),
        new Distance(-FIELD_HEIGHT.inches / 2),
        FIELD_WIDTH,
        FIELD_HEIGHT
    )
    ctx.restore()
}

export function drawCircle(
    x: Distance,
    y: Distance,
    radius: Distance,
    fillColor: string = "blue",
    outlineColor: string = "black",
    outlineWidth: Distance = new Distance(0.02)
) {
    if (!ctx) return
    ctx.beginPath()
    ctx.arc(x.pixels, y.pixels, radius.pixels, 0, 2 * Math.PI)
    ctx.fillStyle = fillColor === "" ? "transparent" : fillColor
    ctx.fill()
    ctx.strokeStyle = outlineColor
    ctx.lineWidth = outlineWidth.pixels
    ctx.stroke()
    ctx.closePath()
}

export function drawRectangle(
    x: Distance,
    y: Distance,
    width: Distance,
    height: Distance,
    fillColor: string = "transparent",
    outlineColor: string = "black",
    outlineWidth: Distance = new Distance(0.02)
) {
    if (!ctx) return
    ctx.save()
    if (fillColor !== "transparent") {
        ctx.fillStyle = fillColor
        ctx.fillRect(x.pixels, y.pixels, width.pixels, height.pixels)
    }
    if (outlineWidth.pixels > 0) {
        ctx.strokeStyle = outlineColor
        ctx.lineWidth = outlineWidth.pixels
        ctx.strokeRect(x.pixels, y.pixels, width.pixels, height.pixels)
    }
    ctx.restore()
}

export function drawLine(
    x1: Distance,
    y1: Distance,
    x2: Distance,
    y2: Distance,
    strokeColor: string = "black",
    strokeWidth: Distance = new Distance(0.05)
) {
    if (!ctx) return
    ctx.save()
    ctx.beginPath()
    ctx.moveTo(x1.pixels, y1.pixels)
    ctx.lineTo(x2.pixels, y2.pixels)
    ctx.strokeStyle = strokeColor
    ctx.lineWidth = strokeWidth.pixels
    ctx.stroke()
    ctx.closePath()
    ctx.restore()
}

export function drawAxesAt(
    x: Distance,
    y: Distance,
    size: Distance = new Distance(1),
    color = "red",
    strokeWidth: Distance = new Distance(0.05),
    showLabel = true
) {
    if (!ctx) return
    ctx.save()
    ctx.beginPath()
    ctx.moveTo(x.pixels - size.pixels, y.pixels)
    ctx.lineTo(x.pixels + size.pixels, y.pixels)
    ctx.moveTo(x.pixels, y.pixels - size.pixels)
    ctx.lineTo(x.pixels, y.pixels + size.pixels)
    ctx.lineWidth = strokeWidth.pixels
    ctx.strokeStyle = color
    ctx.stroke()
    ctx.closePath()

    if (showLabel) {
        ctx.save()
        ctx.translate(x.pixels, y.pixels)
        ctx.rotate(-rotationToRadians(appliedOffsetHeading))
        ctx.font = "12px system-ui"
        ctx.textAlign = "left"
        ctx.textBaseline = "top"
        ctx.fillStyle = color
        ctx.fillText(`(${x.inches.toFixed(2)}", ${y.inches.toFixed(2)}")`, 4, 4)
        ctx.restore()
    }
    ctx.restore()
}
