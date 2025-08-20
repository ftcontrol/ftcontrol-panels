export const BASE_INCHES = 24 * 6
export const BASE_PIXELS = 1024
export const PIXELS_PER_INCH = BASE_PIXELS / BASE_INCHES

export class Distance {
  readonly inches: number
  readonly pixels: number
  readonly tiles: number

  constructor(inches: number, explicitPixels?: number) {
    this.inches = inches
    this.pixels =
      explicitPixels !== undefined ? explicitPixels : inches * PIXELS_PER_INCH
    this.tiles = inches / 24
  }

  static fromPixels(px: number): Distance {
    return new Distance(px / PIXELS_PER_INCH, px)
  }
}

export const FIELD_WIDTH = new Distance(BASE_INCHES, BASE_PIXELS)
export const FIELD_HEIGHT = new Distance(BASE_INCHES, BASE_PIXELS)
