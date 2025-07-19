export type Packet = {
  offsetX: number
  offsetY: number
  rotation: "DEG_0" | "DEG_90" | "DEG_180" | "DEG_270"
  items: Drawable[]
  bgID: string | null
}

export type Drawable = Circle | Rectangle | Line | Image

export const emptyPacket: Packet = {
  offsetX: 0,
  offsetY: 0,
  rotation: "DEG_0",
  items: [],
  bgID: null,
}

export type Circle = {
  x: number
  y: number
  r: number
  style: Style
}

export type Rectangle = {
  x: number
  y: number
  w: number
  h: number
  style: Style
}

export type Line = {
  x1: number
  y1: number
  x2: number
  y2: number
  style: Style
}

export type Image = {
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
