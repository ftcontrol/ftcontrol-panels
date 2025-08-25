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
  private reverseXY = false

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
    this.offsetX = new Distance(packet.preset.offsetX ?? 0)
    this.offsetY = new Distance(packet.preset.offsetY ?? 0)
    this.heading = packet.preset.rotation
    this.flipX = !!packet.preset.flipX
    this.flipY = !!packet.preset.flipY
    this.reverseXY = !!packet.preset.reverseXY
  }

  async draw(packet: Packet, images: ImagesMap): Promise<void> {
    this.prepareCanvas()

    this.ctx.save()
    this.ctx.setTransform(1, 0, 0, 1, 0, 0)
    this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height)
    this.ctx.restore()

    if (packet.bgID && images[packet.bgID]) {
      this.ctx.save()
      this.applyBackgroundTransform()
      await this.drawFieldImage(packet.bgID, images[packet.bgID])
      this.ctx.restore()
    }

    this.ctx.save()
    this.applyItemTransform()
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
    this.ctx.restore()

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

  private applyBackgroundTransform() {
    const W = FIELD_WIDTH.pixels
    const H = FIELD_HEIGHT.pixels
    const theta = rotationToRadians(this.heading)

    this.ctx.setTransform(this.dpr, 0, 0, this.dpr, 0, 0)
    this.ctx.translate(W / 2, H / 2)
    this.ctx.rotate(theta)
  }

  private getItemTransform(): [number, number, number, number, number, number] {
    const W = FIELD_WIDTH.pixels
    const H = FIELD_HEIGHT.pixels

    const sx = this.flipX ? -1 : 1
    const sy = this.flipY ? -1 : 1

    const S00 = this.reverseXY ? 0 : 1
    const S01 = this.reverseXY ? 1 : 0
    const S10 = this.reverseXY ? 1 : 0
    const S11 = this.reverseXY ? 0 : 1

    const B00 = S00 * sx
    const B01 = S01 * sy
    const B10 = S10 * sx
    const B11 = S11 * sy

    const mwc_a = 0,
      mwc_b = 1,
      mwc_c = -1,
      mwc_d = 0

    const L_a = mwc_a * B00 + mwc_b * B10
    const L_b = mwc_a * B01 + mwc_b * B11
    const L_c = mwc_c * B00 + mwc_d * B10
    const L_d = mwc_c * B01 + mwc_d * B11

    const ox = this.offsetX.pixels
    const oy = this.offsetY.pixels
    const tX = L_a * ox + L_b * oy
    const tY = L_c * ox + L_d * oy

    const theta = rotationToRadians(this.heading)
    const cos = Math.cos(theta)
    const sin = Math.sin(theta)

    const a = cos * L_a + -sin * L_c
    const b = sin * L_a + cos * L_c
    const c = cos * L_b + -sin * L_d
    const d = sin * L_b + cos * L_d

    const e = W / 2 + (cos * tX - sin * tY)
    const f = H / 2 + (sin * tX + cos * tY)

    return [a, b, c, d, e, f]
  }

  private applyItemTransform() {
    const [a, b, c, d, e, f] = this.getItemTransform()
    this.ctx.setTransform(
      this.dpr * a,
      this.dpr * b,
      this.dpr * c,
      this.dpr * d,
      this.dpr * e,
      this.dpr * f
    )
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
      await (img as any).decode?.()
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
    x: Distance,
    y: Distance,
    w: Distance,
    h: Distance
  ) {
    const img = await this.getImage(key, base64)
    if (!img) return
    this.ctx.drawImage(img, x.pixels, y.pixels, w.pixels, h.pixels)
  }

  private async drawFieldImage(key: string, base64: string) {
    const img = await this.getImage(key, base64)
    if (!img) return
    const W = FIELD_WIDTH.pixels
    const H = FIELD_HEIGHT.pixels
    this.ctx.drawImage(img, -W / 2, -H / 2, W, H)
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
