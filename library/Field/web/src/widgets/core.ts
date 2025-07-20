import { Distance, FIELD_HEIGHT, FIELD_WIDTH } from "./primitives"

export let ctx: CanvasRenderingContext2D | null

var appliedOffsetX = new Distance(0)
var appliedOffsetY = new Distance(0)
var isInitialized = false

const imageCache: Map<string, HTMLImageElement> = new Map()

export function init(
  canvas: HTMLCanvasElement,
  offsetX: Distance,
  offsetY: Distance
) {
  if (!canvas) return

  if (
    isInitialized &&
    appliedOffsetX.inches == offsetX.inches &&
    appliedOffsetY.inches == offsetY.inches
  ) {
    return
  }

  appliedOffsetX = offsetX
  appliedOffsetY = offsetY

  const dpr = window.devicePixelRatio || 1
  const width = FIELD_WIDTH.pixels * dpr
  const height = FIELD_HEIGHT.pixels * dpr

  canvas.width = width
  canvas.height = height

  ctx = canvas.getContext("2d")
  if (ctx) {
    ctx.scale(dpr, dpr)
  }

  //24 inch > 1TILE > 1/6FIELD

  if (ctx) {
    ctx.translate(
      FIELD_WIDTH.pixels / 2 + offsetX.pixels,
      FIELD_HEIGHT.pixels / 2 + offsetY.pixels
    )
  }

  isInitialized = true
}

export async function drawBase64Image(
  key: string,
  base64: string,
  startX: Distance,
  startY: Distance,
  width: Distance,
  height: Distance
) {
  if (ctx == null) return

  const hasPrefix = base64.startsWith("data:image/png;base64,")
  const src = hasPrefix ? base64 : `data:image/png;base64,${base64}`

  let img: HTMLImageElement

  if (imageCache.has(key)) {
    img = imageCache.get(key)!
  } else {
    img = new Image()
    img.src = src
    await img.decode()
    imageCache.set(key, img)
  }

  ctx.save()
  console.log(startX.pixels, startY.pixels, width.pixels, height.pixels)
  ctx.drawImage(img, startX.pixels, startY.pixels, width.pixels, height.pixels)
  ctx.restore()
}

export function drawFieldImage(key: string, base64: string) {
  drawBase64Image(
    key,
    base64,
    new Distance(-24 * 3 + appliedOffsetX.inches),
    new Distance(-24 * 3 + appliedOffsetY.inches),
    FIELD_WIDTH,
    FIELD_HEIGHT
  )
}

export function drawCircle(
  x: Distance,
  y: Distance,
  radius: Distance,
  fillColor: string = "blue",
  outlineColor: string = "black",
  outlineWidth: Distance = new Distance(0.02)
) {
  if (fillColor == "") fillColor = "transparent"
  if (ctx == null) return
  ctx.beginPath()
  ctx.arc(x.pixels, y.pixels, radius.pixels, 0, 2 * Math.PI)

  ctx.fillStyle = fillColor
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
  if (ctx == null) return

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
  if (ctx == null) return

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
