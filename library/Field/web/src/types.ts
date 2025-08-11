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

export function rotationToRadians(r: CanvasRotation): number {
  switch (r) {
    case CanvasRotation.DEG_0:   return 0;
    case CanvasRotation.DEG_90:  return Math.PI / 2;
    case CanvasRotation.DEG_180: return Math.PI;
    case CanvasRotation.DEG_270: return 3 * Math.PI / 2;
    default: return 0;
  }
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
