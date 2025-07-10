import { dev } from "$app/environment"
import { GlobalSocket, type PluginConfig, type PluginInfo } from "ftc-panels"

export class GlobalState {
  plugins: PluginInfo[] = $state([])
  skippedPlugins: PluginConfig[] = $state([])
  socket: GlobalSocket = new GlobalSocket()

  isConnected = $state(false)

  async init() {
    try {
      this.isConnected = false
      const data = await this.getPluginsUntilReady()

      this.plugins = JSON.parse(data).data.plugins

      this.skippedPlugins = JSON.parse(data).data.skippedPlugins

      await this.socket.init(this.plugins)
      this.isConnected = true
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
