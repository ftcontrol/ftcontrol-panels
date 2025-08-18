import { dev } from "$app/environment"
import {
  GlobalSocket,
  type Notification,
  NotificationsManager,
  type PluginConfig,
  type PluginInfo,
} from "ftc-panels"
import { importFromSource } from "../../../../../../ftcontrol-plugins/cli/core/socket/source"
import { PluginSocket } from "../../../../../../ftcontrol-plugins/cli/core/socket/plugin"
import { deleteValue, readValue, storeValue } from "./indexedDB"
import type { ExtendedTemplateEntry } from "./grid/widgets.svelte"

import pako from "pako"
import { panelsConfig, PanelsManager } from "$lib/manager"

function decompressDeflate(compressed: Uint8Array): string {
  const decompressed = pako.inflate(compressed)
  return new TextDecoder("utf-8").decode(decompressed)
}

export class GlobalState {
  plugins: PluginInfo[] = $state([])

  notifications: Notification[] = $state([])

  notificationsManager: NotificationsManager = new NotificationsManager()

  allTemplates = $derived.by(() => {
    let data: ExtendedTemplateEntry[] = []

    for (const plugin of this.plugins) {
      for (const t of plugin.details.templates) {
        const usedIDs: Set<string> = new Set([])
        const loadedPluginIDs = new Set(this.plugins.map((p) => p.details.id))

        for (const group of t.widgets) {
          for (const widget of group.widgets) {
            usedIDs.add(widget.pluginID)
          }
        }

        for (const navlet of t.navlets) {
          usedIDs.add(navlet.pluginID)
        }

        const missingPlugins = Array.from(usedIDs).filter(
          (id) => !loadedPluginIDs.has(id)
        )

        data.push({
          ...t,
          pluginID: plugin.details.id,
          missingPlugins,
        })
      }
    }

    return data
  })

  skippedPlugins: PluginConfig[] = $state([])
  socket: GlobalSocket = new GlobalSocket()

  isConnected = $state(false)

  updateInterval: ReturnType<typeof setInterval> | null = null

  interval: ReturnType<typeof setInterval> | null = null
  reloadIndexes: Record<string, number> = $state({})
  lastVersionNotificationTime: Record<string, number> = $state({})

  changedTimestamps: Record<string, number> = $state({})

  pluginsTemplatesPreviews: Record<string, string> = $state({})

  isPrepared = $derived.by(() => {
    if (!this.isConnected) return false

    for (const plugin of this.plugins) {
      for (const t of plugin.details.templates) {
        if (!this.pluginsTemplatesPreviews[`${plugin.details.id}/${t.name}`]) {
          return false
        }
      }
    }

    return true
  })

  devPlugins: string[] = $state([])

  hasDevServer = $state(false)

  createDevServerInterval() {
    if (this.interval !== null) {
      clearInterval(this.interval)
    }

    this.interval = setInterval(
      async () => {
        await this.updateDevPlugins(true)
      },
      this.hasDevServer ? 1500 : 10000
    )
  }

  private async updateDevPlugins(reloadManager = false) {
    type LiveChangeEntry = {
      id: string
      name: string
      lastChanged: number
    }

    let livePlugins: LiveChangeEntry[]

    try {
      const res = await this.fetchWithRetry(
        "http://localhost:3001/plugins",
        {},
        1
      )
      livePlugins = await res.json()
      if (!this.hasDevServer) {
        this.createDevServerInterval()
      }
      this.hasDevServer = true
    } catch (error) {
      console.error("Failed to fetch live plugins:", error)
      if (this.hasDevServer) {
        this.createDevServerInterval()
      }
      this.hasDevServer = false
      return
    }

    for (const entry of livePlugins) {
      if (!this.devPlugins.includes(entry.id)) {
        this.devPlugins.push(entry.id)
      }
      if (entry.lastChanged != this.changedTimestamps[entry.id]) {
        console.log("Rebuilding", entry.name)

        const res = await this.fetchWithRetry(
            `http://localhost:3001/plugins/${entry.id}`,
            {},
            1
        )
        let details = await res.json() as PluginConfig

        const response = await this.fetchWithRetry(
            `http://localhost:3001/plugins/${entry.id}/svelte.js`,
            {},
            1
        )
        let svelte = await response.text()

        const { default: Selector } = await importFromSource(
            svelte || ""
        )

        this.socket.pluginSelectors[details.id] = Selector

        if (reloadManager) {
          const Manager = Selector("Manager")
          const oldStateData = this.socket.pluginManagers[details.id].state.data
          this.socket.pluginManagers[details.id] = new Manager(
            new PluginSocket(details.id, this.socket),
            details,
            this.notificationsManager
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

        for (const t of details.templates) {
          const cacheKey = `${entry.id}/${t.name}`
          delete this.pluginsTemplatesPreviews[cacheKey]
        }

        for (const plugin of this.plugins) {
          if (plugin.details.id == entry.id) {
            plugin.details = details
          }
        }

        this.reloadIndexes[details.id]++

        console.log("Reloaded plugin", entry.id)

        this.changedTimestamps[entry.id] = entry.lastChanged
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
      this.plugins = []

      this.hasDevServer = false

      this.notifications = []

      this.notificationsManager = new NotificationsManager()

      this.skippedPlugins = []
      this.socket = new GlobalSocket()

      this.notificationsManager.callbacks = []
      this.notificationsManager.onUpdate((newValue) => {
        this.notifications = newValue
      })
      this.notifications = this.notificationsManager.data
      this.isConnected = false

      const t0 = Date.now()
      const data = await this.getPluginsUntilReady()
      console.log(`[init] getPluginsUntilReady() took ${Date.now() - t0}ms`)

      const parsed = JSON.parse(data)

      this.plugins = parsed.data.plugins

      console.log(`[init] Loaded ${this.plugins.length} plugins`)
      this.plugins.forEach((item) => {
        this.reloadIndexes[item.details.id] = 0
        this.changedTimestamps[item.details.id] = 0
        this.lastVersionNotificationTime[item.details.id] = 0
      })

      this.skippedPlugins = parsed.data.skippedPlugins
      console.log(`[init] Skipped ${this.skippedPlugins.length} plugins`)

      const t1 = Date.now()

      if (this.interval !== null) {
        clearInterval(this.interval)
      }

      const baseURL = dev ? "http://localhost:8001/" : "/"

      const mapping: Record<string, string> = {}

      this.plugins.forEach(
        (it) =>
          (mapping[it.details.id] = `${baseURL}api/svelte/${it.details.id}`)
      )

      await this.socket.initPlugins(
        this.plugins,
        mapping,
        this.notificationsManager
      )

      try{
        await this.updateDevPlugins(true)
      }catch (e){}

      this.plugins.push({
        details: panelsConfig,
        config: {},
      })

      this.socket.pluginManagers["com.bylazar.panels"] = new PanelsManager(
        panelsConfig
      )

      this.createDevServerInterval()
      console.log(`[init] Dev plugin interval set up`)

      await this.socket.initSocket(async () => {
        // await this.init()
        window.location.reload()
      })
      console.log(`[init] socket.init() took ${Date.now() - t1}ms`)

      this.isConnected = true

      if (this.updateInterval !== null) {
        clearInterval(this.updateInterval)
      }

      this.updateInterval = setInterval(async () => {
        const hasInternet = await this.hasInternetConnection()
        if (!hasInternet) return
        console.log("Has internet")
        await this.checkVersions()
      }, 10000)

      console.log(
        `[init] Initialization complete in ${Date.now() - startTime}ms`
      )

      if (
        this.plugins
          .map((it) => it.details.id)
          .includes("com.bylazar.exampleplugin")
      ) {
        this.notificationsManager.addAction("Don't use default plugin id", [
          {
            text: "OK",
            task: () => {},
          },
          {
            text: "Details",
            task: () => {},
          },
        ])
      }
    } catch (e) {
      console.error(`[init] Error during initialization:`, e)
      // await this.init()
      window.location.reload()
    }
  }

  close() {
    this.isConnected = false
    if (this.interval) clearInterval(this.interval)
    this.socket.close()
  }

  private async getPluginsUntilReady(): Promise<string> {
    const currentSha = await this.getSha()

    const cachedSha = await readValue("sha256")
    const cachedText = await readValue("plugins")
    const oldPanelsVersion = await readValue("version")

    if (oldPanelsVersion !== panelsConfig.version) {
      await deleteValue("sha256")
      await deleteValue("plugins")
      await storeValue("version", panelsConfig.version)
      throw Error("Panels version changed")
    }

    if (currentSha == cachedSha && cachedText) {
      setTimeout(async () => {
        const extraText = await this.getPlugins()

        if (extraText == null) return
        if (extraText == cachedText) return
        await storeValue("sha256", currentSha)
        await storeValue("plugins", extraText)
        await storeValue("version", panelsConfig.version)
        // await this.init()
        window.location.reload()
      }, 100)
      return cachedText
    }

    await storeValue("sha256", currentSha)

    const text = await this.getPlugins()

    await storeValue("plugins", text)
    await storeValue("version", panelsConfig.version)

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
      const response = await this.fetchWithRetry(`${url}/api/sha256`, {})

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
      `${url}/api/plugins`,
      {},
      5,
      4000,
      500
    )

    const buffer = await response.arrayBuffer()
    const uint8Array = new Uint8Array(buffer)

    const startTime = performance.now()
    const text = decompressDeflate(uint8Array)
    const endTime = performance.now()
    console.log(`Decompression took ${(endTime - startTime).toFixed(2)} ms`)

    return text
  }

  async checkVersions() {
    const ids = new Set<string>()

    for (const plugin of this.plugins) {
      const includedIDs = plugin.details.includedPluginsIDs
      for (const id of includedIDs) {
        ids.add(id)
      }
    }

    for (const plugin of this.plugins) {
      try {
        const id = plugin.details.id

        const lastTime = this.lastVersionNotificationTime[id] ?? 0

        if (Date.now() - lastTime < 15 * 60 * 1000) continue

        if (ids.has(id)) continue
        const manager = this.socket.pluginManagers[id]

        console.log("Checking plugin version", id, manager.config.version)

        const version = await manager.constructor.getNewVersion()
        let hasVersion = version != manager.config.version
        if (version == "") {
          hasVersion = false
        }

        if (version != "") {
          this.lastVersionNotificationTime[id] = Date.now()
        }

        if (hasVersion) {
          this.notificationsManager.addAction(
            `Plugin ${id} has a new version: ${version}`,
            [
              {
                text: "Check Website",
                task: () => {
                  window.open(plugin.details.websiteURL, "_blank")
                },
              },
              {
                text: "Remind me later",
                task: () => {},
              },
            ]
          )
        } else {
          console.log(`Plugin ${id} is latest`)
        }
      } catch (err) {
        console.error(err)
      }
    }
  }
}
