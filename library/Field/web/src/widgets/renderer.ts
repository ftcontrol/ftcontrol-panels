import { Distance, FIELD_HEIGHT, FIELD_WIDTH } from "./primitives"
import {
  CanvasRotation,
  rotationToRadians,
  type Packet,
  DrawableType,
} from "../types"

type ImagesMap = Record<string, string>

const DEFAULTS = {
  fill: "transparent",
  outlineFill: "black",
  outlineWidth: new Distance(0),
}

function normalizeDataUrl(src: string): string {
  if (!src) return ""
  if (src.startsWith("data:image/")) return src
  return `data:image/png;base64,${src}`
}

export class Renderer {
  private canvas: HTMLCanvasElement
  private ctx: CanvasRenderingContext2D
  private dpr = 1

  private offsetX = new Distance(0)
  private offsetY = new Distance(0)
  private heading: CanvasRotation = CanvasRotation.DEG_0
  private flipX = false
  private flipY = false

  private imageCache = new Map<string, { sig: number; el: HTMLImageElement }>()

  constructor(canvas: HTMLCanvasElement) {
    const ctx = canvas.getContext("2d")
    if (!ctx) throw new Error("2D context unavailable")
    this.canvas = canvas
    this.ctx = ctx
    this.prepareCanvas()
  }

  destroy() {
    this.imageCache.clear()
  }

  setViewport(packet: Packet) {
    this.offsetX = new Distance(packet.preset.offsetX)
    this.offsetY = new Distance(packet.preset.offsetY)
    this.heading = packet.preset.rotation
    this.flipX = packet.preset.flipX
    this.flipY = packet.preset.flipY
  }

  async draw(packet: Packet, images: ImagesMap): Promise<void> {
    this.prepareCanvas()

    this.ctx.save()
    this.ctx.setTransform(1, 0, 0, 1, 0, 0)
    this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height)
    this.ctx.restore()

    if (packet.bgID && images[packet.bgID]) {
      await this.drawFieldImage(packet.bgID, images[packet.bgID])
    }

    this.applyOverlayTransform()
    for (const item of packet.items) {
      switch (item.type) {
        case DrawableType.CIRCLE: {
          const s = item.style ?? {}
          this.drawCircle(
            new Distance(item.x),
            new Distance(item.y),
            new Distance(item.r),
            s.fill ?? DEFAULTS.fill,
            s.outlineFill ?? DEFAULTS.outlineFill,
            new Distance(s.outlineWidth ?? DEFAULTS.outlineWidth.inches)
          )
          break
        }
        case DrawableType.RECTANGLE: {
          const s = item.style ?? {}
          this.drawRectangle(
            new Distance(item.x),
            new Distance(item.y),
            new Distance(item.w),
            new Distance(item.h),
            s.fill ?? DEFAULTS.fill,
            s.outlineFill ?? DEFAULTS.outlineFill,
            new Distance(s.outlineWidth ?? DEFAULTS.outlineWidth.inches)
          )
          break
        }
        case DrawableType.LINE: {
          const s = item.style ?? {}
          this.drawLine(
            new Distance(item.x1),
            new Distance(item.y1),
            new Distance(item.x2),
            new Distance(item.y2),
            s.outlineFill ?? DEFAULTS.outlineFill,
            new Distance(s.outlineWidth ?? new Distance(0.05).inches)
          )
          break
        }
        case DrawableType.IMAGE: {
          await this.drawBase64Image(
            item.id,
            images[item.id],
            new Distance(item.x),
            new Distance(item.y),
            new Distance(item.w),
            new Distance(item.h)
          )
          break
        }
      }
    }

    this.ctx.setTransform(this.dpr, 0, 0, this.dpr, 0, 0)
  }

  private prepareCanvas() {
    const nextDpr = window.devicePixelRatio || 1
    const expectedW = Math.round(FIELD_WIDTH.pixels * nextDpr)
    const expectedH = Math.round(FIELD_HEIGHT.pixels * nextDpr)

    if (
      this.dpr !== nextDpr ||
      this.canvas.width !== expectedW ||
      this.canvas.height !== expectedH
    ) {
      this.dpr = nextDpr
      this.canvas.width = expectedW
      this.canvas.height = expectedH
    }

    this.ctx.imageSmoothingEnabled = true
    this.ctx.imageSmoothingQuality = "high"
    this.ctx.setTransform(this.dpr, 0, 0, this.dpr, 0, 0)
  }

  private applyBaseTransform() {
    this.ctx.setTransform(this.dpr, 0, 0, this.dpr, 0, 0)
    this.ctx.translate(FIELD_WIDTH.pixels / 2, FIELD_HEIGHT.pixels / 2)
    this.ctx.rotate(rotationToRadians(this.heading))
  }

  private applyOverlayTransform() {
    this.ctx.setTransform(this.dpr, 0, 0, this.dpr, 0, 0)
    this.ctx.translate(
      FIELD_WIDTH.pixels / 2 + this.offsetX.pixels,
      FIELD_HEIGHT.pixels / 2 + this.offsetY.pixels
    )
    this.ctx.rotate(rotationToRadians(this.heading))
    this.ctx.scale(this.flipX ? -1 : 1, this.flipY ? -1 : 1)
  }

  private async getImage(
    key: string,
    base64?: string
  ): Promise<HTMLImageElement | null> {
    if (!base64) return null
    const normalized = normalizeDataUrl(base64)
    const sig = normalized.length

    const cached = this.imageCache.get(key)
    if (cached && cached.sig === sig) return cached.el

    const img = new Image()
    img.src = normalized
    try {
      await img.decode()
    } catch {
      await new Promise<void>((res, rej) => {
        img.onload = () => res()
        img.onerror = () => rej(new Error("image decode failed"))
      })
    }
    this.imageCache.set(key, { sig, el: img })
    return img
  }

  private async drawBase64Image(
    key: string,
    base64: string | undefined,
    startX: Distance,
    startY: Distance,
    width: Distance,
    height: Distance
  ) {
    const img = await this.getImage(key, base64)
    if (!img) return
    this.ctx.drawImage(
      img,
      startX.pixels,
      startY.pixels,
      width.pixels,
      height.pixels
    )
  }

  private async drawFieldImage(key: string, base64: string) {
    const img = await this.getImage(key, base64)
    if (!img) return

    const W = FIELD_WIDTH.pixels
    const H = FIELD_HEIGHT.pixels

    this.ctx.save()
    this.applyBaseTransform()
    this.ctx.drawImage(img, -W / 2, -H / 2, W, H)
    this.ctx.restore()
  }

  private drawCircle(
    x: Distance,
    y: Distance,
    radius: Distance,
    fillColor = DEFAULTS.fill,
    outlineColor = DEFAULTS.outlineFill,
    outlineWidth: Distance = DEFAULTS.outlineWidth
  ) {
    this.ctx.beginPath()
    this.ctx.arc(x.pixels, y.pixels, radius.pixels, 0, 2 * Math.PI)
    if (fillColor !== "transparent" && fillColor !== "") {
      this.ctx.fillStyle = fillColor
      this.ctx.fill()
    }
    if (outlineWidth.pixels > 0) {
      this.ctx.strokeStyle = outlineColor
      this.ctx.lineWidth = outlineWidth.pixels
      this.ctx.stroke()
    }
    this.ctx.closePath()
  }

  private drawRectangle(
    x: Distance,
    y: Distance,
    width: Distance,
    height: Distance,
    fillColor = DEFAULTS.fill,
    outlineColor = DEFAULTS.outlineFill,
    outlineWidth: Distance = DEFAULTS.outlineWidth
  ) {
    if (fillColor !== "transparent" && fillColor !== "") {
      this.ctx.fillStyle = fillColor
      this.ctx.fillRect(x.pixels, y.pixels, width.pixels, height.pixels)
    }
    if (outlineWidth.pixels > 0) {
      this.ctx.strokeStyle = outlineColor
      this.ctx.lineWidth = outlineWidth.pixels
      this.ctx.strokeRect(x.pixels, y.pixels, width.pixels, height.pixels)
    }
  }

  private drawLine(
    x1: Distance,
    y1: Distance,
    x2: Distance,
    y2: Distance,
    strokeColor = DEFAULTS.outlineFill,
    strokeWidth: Distance = new Distance(0.05)
  ) {
    this.ctx.beginPath()
    this.ctx.moveTo(x1.pixels, y1.pixels)
    this.ctx.lineTo(x2.pixels, y2.pixels)
    this.ctx.strokeStyle = strokeColor
    this.ctx.lineWidth = strokeWidth.pixels
    this.ctx.stroke()
    this.ctx.closePath()
  }
}
