export enum DrawablesTypes {
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

export type Packet = {
  offsetX: number
  offsetY: number
  rotation: CanvasRotation
  items: Drawable[]
  bgID: string | null
}

export type Drawable = Circle | Rectangle | Line | Image

export const emptyPacket: Packet = {
  offsetX: 0,
  offsetY: 0,
  rotation: CanvasRotation.DEG_0,
  items: [],
  bgID: null,
}

export type Circle = {
  type: DrawablesTypes.CIRCLE
  x: number
  y: number
  r: number
  style: Style
}

export type Rectangle = {
  type: DrawablesTypes.RECTANGLE
  x: number
  y: number
  w: number
  h: number
  style: Style
}

export type Line = {
  type: DrawablesTypes.LINE
  x1: number
  y1: number
  x2: number
  y2: number
  style: Style
}

export type Image = {
  type: DrawablesTypes.IMAGE
  x: number
  y: number
  w: number
  h: number
  id: string
}

export type Style = {
  fill: string
  outlineFill: string
  outlineWidth: number
}
