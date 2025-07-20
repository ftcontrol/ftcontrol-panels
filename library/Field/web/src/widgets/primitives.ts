export class Distance {
  inches: number
  pixels: number
  tiles: number
  constructor(inches: number, pixels?: number) {
    this.inches = inches
    if (pixels !== undefined) {
      this.pixels = pixels
    } else {
      const pixelsPerInch = FIELD_WIDTH.pixels / FIELD_WIDTH.inches
      this.pixels = this.inches * pixelsPerInch
    }
    this.tiles = this.inches / 24
  }
}

export const FIELD_WIDTH = new Distance(24 * 6, 1024)
export const FIELD_HEIGHT = new Distance(24 * 6, 1024)
