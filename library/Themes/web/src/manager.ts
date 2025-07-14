import { PluginManager } from "ftc-panels"
import tinycolor from "tinycolor2"

function setCookie(name: string, value: string, days = 365) {
  const expires = new Date(Date.now() + days * 864e5).toUTCString()
  document.cookie = `${name}=${encodeURIComponent(
    value
  )}; expires=${expires}; path=/`
}

function getCookie(name: string): string | null {
  const cookies = document.cookie
    .split("; ")
    .reduce<Record<string, string>>((acc, cur) => {
      const [key, val] = cur.split("=")
      if (key && val) acc[key] = decodeURIComponent(val)
      return acc
    }, {})
  const data = cookies[name] ?? null

  return data
}

type Theme = {
  baseColor: string
  textColor: string
  brightnessOffset: number
  padding: number
  tintColor: string
}

export default class Manager extends PluginManager {
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
    this.loadFromCookies()
    this.apply()
    this.saveToCookies()

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

  saveToCookies() {
    setCookie("theme", JSON.stringify(this.theme))
  }

  loadFromCookies() {
    const stringData = getCookie("theme")
    if (stringData != null) {
      this.theme = JSON.parse(stringData)
    }
  }

  reset() {
    this.theme = this.DEFAULT_THEME
    this.saveToCookies()
    this.apply()
  }
}
