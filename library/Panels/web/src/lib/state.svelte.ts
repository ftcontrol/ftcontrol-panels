import { dev } from "$app/environment"
import {
  GlobalSocket,
  type DevPluginEntry,
  type PanelsWidget,
  type PluginConfig,
  type PluginInfo,
} from "ftc-panels"
import { importFromSource } from "../../../../../../ftcontrol-plugins/cli/core/socket/source"
import { PluginSocket } from "../../../../../../ftcontrol-plugins/cli/core/socket/plugin"
import LZMA from "./lzma"
import { readValue, storeValue } from "./indexedDB"

function decompressLzma(compressedData: Uint8Array): Promise<string> {
  return new Promise((resolve, reject) => {
    LZMA.decompress(compressedData, (result: string | Uint8Array | Error) => {
      if (result instanceof Error) {
        reject(result)
      } else if (typeof result === "string") {
        resolve(result)
      } else {
        const decoder = new TextDecoder("utf-8")
        resolve(decoder.decode(result))
      }
    })
  })
}

export class GlobalState {
  plugins: PluginInfo[] = $state([])
  devServers: DevPluginEntry[] = $state([])
  skippedPlugins: PluginConfig[] = $state([])
  socket: GlobalSocket = new GlobalSocket()

  isConnected = $state(false)

  updateInterval: ReturnType<typeof setInterval> | null = null

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
  lastVersionNotificationTime: Record<string, number> = $state({})

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
              details.widgets.map(async (widget: PanelsWidget) => {
                const data = await this.getFromServer(
                  dev.devURL,
                  `widgets/${widget.name}.js`
                )
                widget.textContent = data
              })
            )

            await Promise.all(
              details.navlets.map(async (navlet: PanelsWidget) => {
                const data = await this.getFromServer(
                  dev.devURL,
                  `navlets/${navlet.name}.js`
                )
                navlet.textContent = data
              })
            )

            const homepage = await this.getFromServer(
              dev.devURL,
              `/docs/${details.docs.homepage.name}.js`
            )

            details.docs.homepage.textContent = homepage

            await Promise.all(
              details.docs.chapters.map(async (chapter: PanelsWidget) => {
                const data = await this.getFromServer(
                  dev.devURL,
                  `docs/${chapter.name}.js`
                )
                chapter.textContent = data
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
                  new PluginSocket(details.id, this.socket),
                  details
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

  async hasInternetConnection(): Promise<boolean> {
    try {
      const response = await fetch(
        "https://raw.githubusercontent.com/lazarcloud/ftcontrol-maven/refs/heads/main/dev/com/bylazar/panels/maven-metadata.xml",
        {
          cache: "no-cache",
        }
      )
      return response.ok
    } catch (e) {
      return false
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
        this.lastVersionNotificationTime[item.details.id] = 0
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
      await this.socket.init(this.plugins, () => {
        window.location.reload()
      })
      console.log(`[init] socket.init() took ${Date.now() - t1}ms`)

      if (this.devServers.length) {
        this.interval = setInterval(() => {
          this.updateDevPlugins(true)
        }, 1000)
        console.log(`[init] Dev plugin interval set up`)
      }

      if (this.updateInterval !== null) {
        clearInterval(this.updateInterval)
      }

      this.updateInterval = setInterval(async () => {
        const hasInternet = await this.hasInternetConnection()
        if (!hasInternet) return
        console.log("Has internet")
        await this.checkVersions()
      }, 1000)

      this.isConnected = true
      console.log(
        `[init] Initialization complete in ${Date.now() - startTime}ms`
      )
    } catch (e) {
      console.error(`[init] Error during initialization:`, e)
      window.location.reload()
    }
  }

  close() {
    this.isConnected = false
    if (this.interval) clearInterval(this.interval)
    this.socket.close()
  }

  private async getPluginsUntilReady(): Promise<string> {
    var currentSha = await this.getSha()

    var cachedSha = await readValue("sha256")
    var cachedText = await readValue("plugins")

    if (currentSha == cachedSha && cachedText) {
      setTimeout(async () => {
        const extraText = await this.getPlugins()

        if (extraText == null) return
        if (extraText == cachedText) return
        await storeValue("sha256", currentSha)
        await storeValue("plugins", extraText)
        window.location.reload()
      }, 100)
      return cachedText
    }

    await storeValue("sha256", currentSha)

    const text = await this.getPlugins()

    await storeValue("plugins", text)

    return text
  }

  private fetchWithTimeout(url: string, options = {}, timeout = 5000) {
    const controller = new AbortController()
    const timer = setTimeout(() => controller.abort(), timeout)

    return fetch(url, { ...options, signal: controller.signal }).finally(() =>
      clearTimeout(timer)
    )
  }

  private fetchWithRetry(
    url: string,
    options = {},
    retries = 3,
    timeout = 1000,
    delay = 250
  ): Promise<Response> {
    return this.fetchWithTimeout(url, options, timeout).catch((error) => {
      if (retries > 0) {
        console.warn(`Retrying... (${retries} left), Error: ${error.message}`)
        return new Promise((resolve) => setTimeout(resolve, delay)).then(() =>
          this.fetchWithRetry(url, options, retries - 1, timeout, delay * 2)
        )
      }
      throw error
    })
  }

  private async getSha(attempts = 0): Promise<string> {
    if (attempts > 5) {
      throw Error("Tried too many times.")
    }
    const url = dev ? "http://localhost:8001" : window.location.origin

    try {
      const response = await this.fetchWithRetry(`${url}/sha256`, {})

      const sha = await response.text()

      if (sha && sha.trim() != "null") {
        return sha
      }
      return this.getSha(attempts + 1)
    } catch (err) {
      console.warn("Fetch failed, retrying...", err)
      throw err
    }
  }

  private async getPlugins(): Promise<string> {
    const url = dev ? "http://localhost:8001" : window.location.origin

    const response = await this.fetchWithRetry(
      `${url}/plugins`,
      {},
      5,
      4000,
      500
    )

    const buffer = await response.arrayBuffer()
    const uint8Array = new Uint8Array(buffer)

    const startTime = performance.now()
    const text = await decompressLzma(uint8Array)
    const endTime = performance.now()
    console.log(
      `LZMA decompression took ${(endTime - startTime).toFixed(2)} ms`
    )

    return text
  }

  panelsVersion = "0.0.2"

  async getLatestVersion(): Promise<string> {
    try {
      const response = await fetch(
        `https://raw.githubusercontent.com/lazarcloud/ftcontrol-maven/refs/heads/main/dev/com/bylazar/panels/maven-metadata.xml`
      )
      if (!response.ok) throw new Error(`HTTP ${response.status}`)

      const xmlText = await response.text()
      const parser = new DOMParser()
      const xmlDoc = parser.parseFromString(xmlText, "application/xml")

      const latestVersion = xmlDoc.querySelector("latest")?.textContent

      return latestVersion || ""
    } catch (error) {
      return ""
    }
  }

  async checkVersions() {
    const combinedPlugins = [
      "com.bylazar.battery",
      "com.bylazar.capture",
      "com.bylazar.configurables",
      "com.bylazar.docs",
      "com.bylazar.exampleplugin",
      "com.bylazar.field",
      "com.bylazar.gamepad",
      "com.bylazar.limelightproxy",
      "com.bylazar.opmodecontrol",
      "com.bylazar.telemetry",
      "com.bylazar.themes",
      //TODO: fill here
    ]
    var isCombined = false
    for (const plugin of this.plugins) {
      if (plugin.details.id == "com.bylazar.fullpanels") {
        isCombined = true
      }
    }

    console.log("isCombined", isCombined)

    for (const plugin of this.plugins) {
      const id = plugin.details.id

      if (Date.now() - this.lastVersionNotificationTime[id] < 15 * 60 * 1000)
        return

      if (isCombined && combinedPlugins.includes(id)) continue
      const manager = this.socket.pluginManagers[id]

      console.log("Checking plugin version", id, manager.config.version)

      const hasVersion = await manager.hasNewVersion(manager.config.version)

      if (hasVersion) {
        this.lastVersionNotificationTime[id] = Date.now()
        alert(`Plugin ${id} has a new version`)
      } else {
        console.log(`Plugin ${id} is latest`)
      }
    }

    if (!isCombined) {
      if (
        Date.now() - this.lastVersionNotificationTime["panels"] <
        15 * 60 * 1000
      )
        return
      const version = await this.getLatestVersion()
      if (version != this.panelsVersion) {
        this.lastVersionNotificationTime["panels"] = Date.now()
        alert(`Panels has a new version`)
      } else {
        alert(`Panels is latest`)
      }
    }
  }
}
