type HexRGB = `#${string}`
type UnitHue = number & { readonly __brand: "UnitHue_0to1" }

interface Stop {
  readonly h: UnitHue
  readonly hex: HexRGB
}

const OFF_HEX: HexRGB = "#000000"
const WHITE_HEX: HexRGB = "#ffffff"

const RED_H = 0.2777 as UnitHue
const ORANGE_H = 0.333 as UnitHue
const YELLOW_H = 0.388 as UnitHue
const SAGE_H = 0.444 as UnitHue
const GREEN_H = 0.5 as UnitHue
const AZURE_H = 0.555 as UnitHue
const BLUE_H = 0.611 as UnitHue
const INDIGO_H = 0.666 as UnitHue
const VIOLET_H = 0.722 as UnitHue

const STOPS = [
  { h: RED_H, hex: "#ff0300" },
  { h: ORANGE_H, hex: "#ff9c00" },
  { h: YELLOW_H, hex: "#fdfd00" },
  { h: SAGE_H, hex: "#6db600" },
  { h: GREEN_H, hex: "#007f0b" },
  { h: AZURE_H, hex: "#007582" },
  { h: BLUE_H, hex: "#006bfe" },
  { h: INDIGO_H, hex: "#3c02ff" },
  { h: VIOLET_H, hex: "#7500ff" },
] as const satisfies readonly Stop[]

const clamp01 = (x: number): UnitHue => Math.min(1, Math.max(0, x)) as UnitHue

const isHexRgb = (s: string): s is HexRGB => /^#[0-9a-fA-F]{6}$/.test(s)

const lerp = (a: number, b: number, t: number) => a + (b - a) * t

const hexToRgb = (hex: HexRGB) => {
  const m = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex)!
  return {
    r: parseInt(m[1], 16),
    g: parseInt(m[2], 16),
    b: parseInt(m[3], 16),
  }
}

const rgbToHex = (r: number, g: number, b: number): HexRGB => {
  const to2 = (v: number) => Math.round(v).toString(16).padStart(2, "0")
  const hex = `#${to2(r)}${to2(g)}${to2(b)}`
  if (!isHexRgb(hex)) throw new Error("Internal hex format error")
  return hex
}

const lerpHex = (hexA: HexRGB, hexB: HexRGB, t: number): HexRGB => {
  const A = hexToRgb(hexA)
  const B = hexToRgb(hexB)
  return rgbToHex(lerp(A.r, B.r, t), lerp(A.g, B.g, t), lerp(A.b, B.b, t))
}

export function hueToHex(h: number): HexRGB {
  const u = clamp01(h)
  if (u < RED_H) return OFF_HEX
  if (u > VIOLET_H) return WHITE_HEX
  let lo: Stop = STOPS[0]
  for (let i = 1; i < STOPS.length; i++) {
    const hi: Stop = STOPS[i]
    if (u <= hi.h) {
      const t = Number((u - lo.h) / (hi.h - lo.h))
      return lerpHex(lo.hex as HexRGB, hi.hex as HexRGB, t)
    }
    lo = hi
  }
  return STOPS[STOPS.length - 1].hex
}
