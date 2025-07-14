import { dev } from "$app/environment"
import {
  GlobalSocket,
  type DevPluginEntry,
  type PluginConfig,
  type PluginInfo,
} from "ftc-panels"

export class GlobalState {
  plugins: PluginInfo[] = $state([])
  devServers: DevPluginEntry[] = $state([])
  skippedPlugins: PluginConfig[] = $state([])
  socket: GlobalSocket = new GlobalSocket()

  isConnected = $state(false)

  private async getJsonFromServer(
    serverURL: string,
    path: string
  ): Promise<any> {
    const url = `${serverURL.replace(/\/+$/, "")}/${path.replace(/^\/+/, "")}`

    try {
      const response = await fetch(url)
      if (!response.ok) {
        throw new Error(
          `Failed to fetch JSON: ${response.status} ${response.statusText}`
        )
      }
      const data = await response.json()
      return data
    } catch (err) {
      console.error(`[getJsonFromServer] Error fetching ${url}:`, err)
      throw err
    }
  }

  interval: ReturnType<typeof setInterval> | null = null

  private async updateDevPlugins() {
    console.log("Updating plugins")
    for (const dev of this.devServers) {
      for (const plugin of this.plugins) {
        if (dev.pluginID == plugin.details.id) {
          try {
            const details = await this.getJsonFromServer(
              dev.devURL,
              "/config.json"
            )

            const manager = await this.getJsonFromServer(
              dev.devURL,
              "/Manager.js"
            )

            details.manager.textContent = manager

            details.widgets.forEach(async (widget: any, index: number) => {
              const data = await this.getJsonFromServer(
                dev.devURL,
                `widgets/${widget.name}.js`
              )
              details.widgets[index].textContent = data
            })
            plugin.details = details
          } catch (e) {
            console.error("Failed to refresh plugin", dev.pluginID)
          }
        }
      }
    }
  }

  async init() {
    try {
      this.isConnected = false
      const data = await this.getPluginsUntilReady()

      this.plugins = JSON.parse(data).data.plugins

      this.skippedPlugins = JSON.parse(data).data.skippedPlugins

      await this.socket.init(this.plugins)

      this.socket.addMessageHandler("core", "devServers", (data) => {
        this.devServers = data
        if (this.devServers.length) {
          this.interval = setInterval(() => {
            this.updateDevPlugins()
          }, 1000)
        }
      })

      this.isConnected = true
    } catch (e) {
      console.error(e)
    }
  }

  close() {
    this.isConnected = false
    if (this.interval) clearInterval(this.interval)
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
