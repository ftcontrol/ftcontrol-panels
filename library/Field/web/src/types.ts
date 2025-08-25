export enum DrawableType {
  CIRCLE = "CIRCLE",
  RECTANGLE = "RECTANGLE",
  LINE = "LINE",
  IMAGE = "IMAGE",
}

export enum CanvasRotation {
  DEG_0 = "DEG_0",
  DEG_90 = "DEG_90",
  DEG_180 = "DEG_180",
  DEG_270 = "DEG_270",
}

export function rotationToRadians(r: CanvasRotation): number {
  switch (r) {
    case CanvasRotation.DEG_0:
      return 0
    case CanvasRotation.DEG_90:
      return Math.PI / 2
    case CanvasRotation.DEG_180:
      return Math.PI
    case CanvasRotation.DEG_270:
      return (3 * Math.PI) / 2
    default:
      return 0
  }
}

export type Packet = {
  preset: FieldPresetParams
  items: Drawable[]
  bgID: string | null
}

export type FieldPresetParams = {
  name: string
  offsetX: number
  offsetY: number
  rotation: CanvasRotation
  flipX: boolean
  flipY: boolean
  reverseXY: boolean
}

export type Drawable = Circle | Rectangle | Line | ImageDrawable

export const emptyPreset: FieldPresetParams = {
  name: "NONE",
  offsetX: 0.0,
  offsetY: 0.0,
  rotation: CanvasRotation.DEG_0,
  flipX: false,
  flipY: true,
  reverseXY: false,
}

export const emptyPacket: Packet = {
  preset: emptyPreset,
  items: [],
  bgID: null,
}

export type Style = {
  fill?: string
  outlineFill?: string
  outlineWidth?: number
}

export type Circle = {
  type: DrawableType.CIRCLE
  x: number
  y: number
  r: number
  style?: Style
}

export type Rectangle = {
  type: DrawableType.RECTANGLE
  x: number
  y: number
  w: number
  h: number
  style?: Style
}

export type Line = {
  type: DrawableType.LINE
  x1: number
  y1: number
  x2: number
  y2: number
  style?: Style
}

export type ImageDrawable = {
  type: DrawableType.IMAGE
  x: number
  y: number
  w: number
  h: number
  id: string
}
