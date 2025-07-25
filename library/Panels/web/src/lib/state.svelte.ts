import { dev } from "$app/environment"
import {
  GlobalSocket,
  type DevPluginEntry,
  type PluginConfig,
  type PluginInfo,
} from "ftc-panels"
import { importFromSource } from "../../../../../../ftcontrol-plugins/cli/core/socket/source"
import { PluginSocket } from "../../../../../../ftcontrol-plugins/cli/core/socket/plugin"
import pako from "pako"

export class GlobalState {
  plugins: PluginInfo[] = $state([])
  devServers: DevPluginEntry[] = $state([])
  skippedPlugins: PluginConfig[] = $state([])
  socket: GlobalSocket = new GlobalSocket()

  isConnected = $state(false)

  private async getFromServer(serverURL: string, path: string): Promise<any> {
    const url = `${serverURL.replace(/\/+$/, "")}/${path.replace(/^\/+/, "")}`

    const timeout = 5000
    const interval = 500
    const maxAttempts = Math.ceil(timeout / interval)
    let attempt = 0

    while (attempt < maxAttempts) {
      try {
        const response = await fetch(url)
        if (!response.ok) {
          throw new Error(
            `Failed to fetch: ${response.status} ${response.statusText}`
          )
        }

        const data = await response.text()
        return data
      } catch (err) {
        attempt++
        if (attempt >= maxAttempts) {
          console.error(`Failed after ${attempt} attempts:`, err)
          throw err
        }
        await new Promise((res) => setTimeout(res, interval))
      }
    }

    throw new Error(`Unexpected error while fetching ${url}`)
  }

  interval: ReturnType<typeof setInterval> | null = null
  reloadIndexes: Record<string, number> = $state({})

  private async updateDevPlugins(reloadManager = false) {
    for (const dev of this.devServers) {
      for (const plugin of this.plugins) {
        if (dev.pluginID == plugin.details.id) {
          try {
            let details = JSON.parse(
              await this.getFromServer(dev.devURL, "/config.json")
            )

            const manager = await this.getFromServer(dev.devURL, "/Manager.js")

            details.manager.textContent = manager

            await Promise.all(
              details.widgets.map(async (widget: any) => {
                const data = await this.getFromServer(
                  dev.devURL,
                  `widgets/${widget.name}.js`
                )
                widget.textContent = data
              })
            )

            if (JSON.stringify(details) != JSON.stringify(plugin.details)) {
              if (reloadManager) {
                const { default: Manager } = await importFromSource(
                  details.manager.textContent || ""
                )

                const oldStateData =
                  this.socket.pluginManagers[details.id].state.data

                this.socket.pluginManagers[details.id] = new Manager(
                  new PluginSocket(details.id, this.socket)
                )

                this.socket.pluginManagers[details.id].state.data = oldStateData

                for (const item of Object.values(
                  this.socket.pluginManagers[details.id].state.data
                )) {
                  for (const callback of item.callbacks) {
                    callback(item.value)
                  }
                }

                this.socket.pluginManagers[details.id]?.onInit()
              }

              plugin.details = details

              this.reloadIndexes[details.id]++

              console.log("Reloaded plugin", dev.pluginID)
            }
          } catch (e) {
            console.error("Failed to refresh plugin", dev.pluginID, e)
          }
        }
      }
    }
  }

  async init() {
    const startTime = Date.now()
    console.log(`[init] Starting initialization...`)

    try {
      this.isConnected = false

      const t0 = Date.now()
      const data = await this.getPluginsUntilReady()
      console.log(`[init] getPluginsUntilReady() took ${Date.now() - t0}ms`)

      const parsed = JSON.parse(data)
      this.plugins = parsed.data.plugins

      console.log(`[init] Loaded ${this.plugins.length} plugins`)
      this.plugins.forEach((item) => {
        this.reloadIndexes[item.details.id] = 0
      })

      this.skippedPlugins = parsed.data.skippedPlugins
      console.log(`[init] Skipped ${this.skippedPlugins.length} plugins`)

      this.devServers = parsed.data.devServers
      console.log(`[init] Found ${this.devServers.length} dev servers`)

      if (this.devServers.length) {
        console.log(`[init] Updating dev plugins (initial)...`)
        this.updateDevPlugins(false)
      }

      const t1 = Date.now()
      await this.socket.init(this.plugins)
      console.log(`[init] socket.init() took ${Date.now() - t1}ms`)

      if (this.devServers.length) {
        this.interval = setInterval(() => {
          this.updateDevPlugins(true)
        }, 1000)
        console.log(`[init] Dev plugin interval set up`)
      }

      this.isConnected = true
      console.log(
        `[init] Initialization complete in ${Date.now() - startTime}ms`
      )
    } catch (e) {
      console.error(`[init] Error during initialization:`, e)
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

        const buffer = await response.arrayBuffer()

        const decompressed = pako.ungzip(new Uint8Array(buffer), {
          to: "string",
        })

        if (decompressed && decompressed.trim() != "null") {
          return decompressed
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
