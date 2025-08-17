import { PluginManager } from "ftc-panels"
import tinycolor from "tinycolor2"

type Theme = {
  baseColor: string
  textColor: string
  brightnessOffset: number
  padding: number
  tintColor: string
}

export default class Manager extends PluginManager {
  GENERATED_COLORS_KEY = "GENERATED_COLOR"
  DEFAULT_THEME: Theme = {
    baseColor: "#040606",
    textColor: "#ffffff",
    brightnessOffset: 12,
    padding: 1,
    tintColor: "#1C1C1C",
  }
  theme: Theme = this.DEFAULT_THEME
  generatedColors: string[] = []

  override onInit(): void {
    this.loadFromLocalStorage()
    this.apply()
    this.saveToLocalStorage()

    this.state.update(this.GENERATED_COLORS_KEY, [
      ...this.generatedColors,
      this.theme.textColor,
    ])

    console.log("Current CSS Variables:", this.theme)
  }

  private applyTint(color: string, tint: string): string {
    return tinycolor.mix(color, tint, 30).toHexString()
  }

  private adjustBrightness(color: string, offset: number): string {
    return tinycolor(color).brighten(offset).toHexString()
  }

  apply() {
    const root = document.documentElement.style
    this.generatedColors = Array.from({ length: 3 }, (_, i) => {
      let brightened = this.adjustBrightness(
        this.theme.baseColor,
        i * this.theme.brightnessOffset
      )
      return this.applyTint(brightened, this.theme.tintColor)
    })
    this.state.update(this.GENERATED_COLORS_KEY, [
      ...this.generatedColors,
      this.theme.textColor,
    ])

    if (this.generatedColors.length >= 3) {
      root.setProperty("--bgDark", this.generatedColors[0])
      root.setProperty("--bgMedium", this.generatedColors[1])
      root.setProperty("--bgLight", this.generatedColors[2])
    }
    if (this.theme.padding != null) {
      root.setProperty("--padding", `${this.theme.padding}rem`)
    }
    if (this.theme.textColor != null) {
      root.setProperty("--text", this.theme.textColor)
    }
  }

  saveToLocalStorage() {
    localStorage.setItem("theme", JSON.stringify(this.theme))
  }

  loadFromLocalStorage() {
    const stringData = localStorage.getItem("theme")
    if (stringData != null) {
      this.theme = JSON.parse(stringData)
    }
  }

  reset() {
    this.theme = this.DEFAULT_THEME
    this.saveToLocalStorage()
    this.apply()
  }

  static async getNewVersion(): Promise<string> {
    return ""
  }
}
