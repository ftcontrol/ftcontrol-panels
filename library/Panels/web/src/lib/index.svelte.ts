import { dev } from "$app/environment"
import { GlobalSocket } from "ftc-panels/src/core/socket/global"
import type { PluginInfo } from "ftc-panels/src/core/types"

class GlobalState {
  plugins: PluginInfo[] = $state([])
  socket: GlobalSocket = new GlobalSocket()

  async init() {
    try {
      const data = await this.getPluginsUntilReady()

      global.plugins = JSON.parse(data).data.plugins

      this.socket.init(global.plugins)
    } catch (e) {
      console.error(e)
    }
  }

  close() {
    this.socket.close()
  }

  private async getPluginsUntilReady(): Promise<string> {
    const url = dev ? "http://localhost:8001" : window.location.origin
    let attempts = 0
    let maxAttempts = 20

    while (attempts < maxAttempts) {
      try {
        const response = await fetch(`${url}/plugins`)
        const text = await response.text()

        if (text && text.trim() != "null") {
          return text
        }
      } catch (err) {
        console.warn("Fetch failed, retrying...", err)
      }

      attempts++
      await new Promise((resolve) => setTimeout(resolve, 500))
    }

    throw new Error("Failed to get plugins after multiple attempts.")
  }
}

export const global = new GlobalState()
