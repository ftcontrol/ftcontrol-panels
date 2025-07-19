export type Packet = {
  offsetX: number
  offsetY: number
  rotation: "DEG_0" | "DEG_90" | "DEG_180" | "DEG_270"
  circles: Circle[]
  rectangles: Rectangle[]
  lines: Line[]
}

export const emptyPacket: Packet = {
  offsetX: 0,
  offsetY: 0,
  rotation: "DEG_0",
  circles: [],
  rectangles: [],
  lines: [],
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

export type Style = {
  fill: string
  outlineFill: string
  outlineWidth: number
}
